package com.SearchService.dto;

import com.SearchService.model.Car;
import com.SearchService.model.RentalCompany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResponse {
    private Car car;
    private RentalCompany company;
}

