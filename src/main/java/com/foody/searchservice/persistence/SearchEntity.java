package com.foody.searchservice.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "search_items")
public class SearchEntity {
    @Id
    private String id;

    private Long recipeId;
    private String title;
    private String description;

}
