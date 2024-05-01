package com.foody.searchservice.controller;

import com.foody.searchservice.business.SearchServiceImpl;
import com.foody.searchservice.dto.RecipeSearchResultDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {
    private final SearchServiceImpl searchService;

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeSearchResultDTO>> searchRecipes(@RequestParam(required = false) String searchTerm) {
        List<RecipeSearchResultDTO> searchResults = searchService.searchRecipes(searchTerm);
        return ResponseEntity.ok(searchResults);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody RecipeSearchResultDTO request) {
        searchService.create(request);
    }
}
