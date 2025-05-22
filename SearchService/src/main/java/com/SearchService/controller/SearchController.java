package com.SearchService.controller;

import com.SearchService.dto.SearchResponse;
import com.SearchService.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<SearchResponse> searchCars(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double maxRate) {
        return searchService.search(keyword, city, category, maxRate);
    }
}
