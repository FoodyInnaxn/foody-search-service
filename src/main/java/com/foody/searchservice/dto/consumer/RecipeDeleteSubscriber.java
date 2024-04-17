package com.foody.searchservice.dto.consumer;

import com.foody.searchservice.configuration.RabbitMQConfig;
import com.foody.searchservice.persistence.SearchRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeDeleteSubscriber {

    @Autowired
    private SearchRepository searchRepository;

//    @RabbitListener(queues = RabbitMQConfig.DELETE_QUEUE)
//    public void receiveRecipeDeletedEvent(Long event) {
//        System.out.println("event: " + event);
//
//        searchRepository.deleteByRecipeId(event);
//        System.out.println("Deleted search with recipe id " + event);
//    }
}
