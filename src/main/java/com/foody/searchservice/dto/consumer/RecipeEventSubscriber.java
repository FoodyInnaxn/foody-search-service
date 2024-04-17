package com.foody.searchservice.dto.consumer;

import com.foody.searchservice.configuration.RabbitMQConfig;
import com.foody.searchservice.persistence.SearchEntity;
import com.foody.searchservice.persistence.SearchRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeEventSubscriber {
    @Autowired
    private SearchRepository recipeSearchRepository;

//    @RabbitListener(queues = RabbitMQConfig.QUEUE, bindings = {})
//    public void receiveRecipeCreatedEvent(RecipeCreatedEvent event) {
//        SearchEntity recipeSearchEntity = new SearchEntity();
//        recipeSearchEntity.setRecipeId(event.getRecipeId());
//        recipeSearchEntity.setTitle(event.getTitle());
//        recipeSearchEntity.setDescription(event.getDescription());
//
//        recipeSearchRepository.save(recipeSearchEntity);
//
//        System.out.println("Received recipe created event: " + event.getTitle() + event.getDescription() + event.getRecipeId());
//    }
}
