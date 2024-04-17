package com.foody.searchservice.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchRepository extends MongoRepository<SearchEntity, Long> {
    void deleteByRecipeId(Long id);
    Optional<SearchEntity> findByRecipeId(Long recipeId);
}
