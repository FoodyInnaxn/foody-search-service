package com.foody.searchservice.dto.consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeCreatedEvent {
    private Long recipeId;
    private String title;
    private String description;
}
