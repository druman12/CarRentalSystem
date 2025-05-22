package com.SearchService.service;

import com.SearchService.dto.SearchResponse;

import java.util.List;

public interface SearchService {
    List<SearchResponse> search(String keyword, String city, String category, Double maxRate);
}

