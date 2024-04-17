package com.foody.searchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSearchResultDTO {
    private Long recipeId;
    private String title;
    private String description;
}
