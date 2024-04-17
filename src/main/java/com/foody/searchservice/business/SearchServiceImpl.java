package com.foody.searchservice.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foody.searchservice.configuration.RabbitMQConfig;
import com.foody.searchservice.dto.RecipeSearchResultDTO;
import com.foody.searchservice.dto.consumer.RecipeCreatedEvent;
import com.foody.searchservice.persistence.SearchEntity;
import com.foody.searchservice.persistence.SearchRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class SearchServiceImpl {
    @Autowired
    private SearchRepository recipeSearchRepository;
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;

    public void create(RecipeSearchResultDTO dto) {
        SearchEntity searchEntity = new SearchEntity();
        searchEntity.setTitle(dto.getTitle());
        searchEntity.setRecipeId(dto.getRecipeId());
        searchEntity.setDescription(dto.getDescription());
        this.recipeSearchRepository.save(searchEntity);

    }

    @RabbitListener(queues = RabbitMQConfig.RECIPE_QUEUE)
    public void handleRecipeCreatedEventMessage(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        SearchEntity searchEntity = new SearchEntity();

        if (routingKey.equals(RabbitMQConfig.RECIPE_ROUTING_KEY_CREATE)) {
            RecipeCreatedEvent recipeFileEvent;
            try {
                recipeFileEvent = objectMapper.readValue(message.getBody(), RecipeCreatedEvent.class);
                searchEntity.setTitle(recipeFileEvent.getTitle());
                searchEntity.setDescription(recipeFileEvent.getDescription());
                searchEntity.setRecipeId(recipeFileEvent.getRecipeId());
                recipeSearchRepository.save(searchEntity);

                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        if (routingKey.equals(RabbitMQConfig.RECIPE_ROUTING_KEY_UPDATE)) {
            RecipeCreatedEvent recipeFileEvent;
            try {
                recipeFileEvent = objectMapper.readValue(message.getBody(), RecipeCreatedEvent.class);
                Optional<SearchEntity> optionalSearchEntity = recipeSearchRepository.findByRecipeId(recipeFileEvent.getRecipeId());

                if(optionalSearchEntity.isPresent()){
                    searchEntity = optionalSearchEntity.get();
                    searchEntity.setTitle(recipeFileEvent.getTitle());
                    searchEntity.setDescription(recipeFileEvent.getDescription());
                    recipeSearchRepository.save(searchEntity);
                }
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<RecipeSearchResultDTO> searchRecipes(String searchTerm) {
        Query query = new Query();
        if (!StringUtils.isEmpty(searchTerm)) {
            Criteria criteria = new Criteria().orOperator(
                    Criteria.where("title").regex(searchTerm, "i"), // Case-insensitive match for title
                    Criteria.where("description").regex(searchTerm, "i") // Case-insensitive match for description
            );
            query.addCriteria(criteria);
        }

        List<SearchEntity> recipes = mongoTemplate.find(query, SearchEntity.class);
        return recipes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private RecipeSearchResultDTO mapToDTO(SearchEntity entity) {
        RecipeSearchResultDTO dto = new RecipeSearchResultDTO();
        dto.setRecipeId(entity.getRecipeId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    @RabbitListener(queues = RabbitMQConfig.FANOUT_SEARCH_QUEUE)
    @Transactional
    public void handleDelete(Long recipeId) {
        Optional<SearchEntity> searchEntities = recipeSearchRepository.findByRecipeId(recipeId);
        if(searchEntities.isPresent()){
            recipeSearchRepository.deleteByRecipeId(recipeId);
        }
    }
}
