package com.SearchService.service;

import com.SearchService.dto.SearchResponse;
import com.SearchService.model.Car;
import com.SearchService.model.RentalCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final ResilientService resilientService;

    @Autowired
    public SearchServiceImpl(ResilientService resilientService) {
        this.resilientService = resilientService;
    }

    @Override
    public List<SearchResponse> search(String keyword, String city, String category, Double maxRate) {
        List<Car> cars = resilientService.getAllCars();
        List<RentalCompany> companies = resilientService.getAllCompanies();

        return cars.stream()
                .filter(car -> car.getStatus().equalsIgnoreCase("available"))
                .filter(car -> keyword == null || car.getMake().toLowerCase().contains(keyword.toLowerCase()) || car.getModel().toLowerCase().contains(keyword.toLowerCase()))
                .filter(car -> category == null || car.getCategory().equalsIgnoreCase(category))
                .filter(car -> maxRate == null || car.getDailyRate() <= maxRate)
                .map(car -> {
                    RentalCompany company = companies.stream()
                            .filter(c -> c.getCompanyId().equals(car.getCompanyId()))
                            .findFirst()
                            .orElse(null);
                    return new SearchResponse(car, company);
                })
                .filter(sr -> city == null || (sr.getCompany() != null && sr.getCompany().getCity().equalsIgnoreCase(city)))
                .collect(Collectors.toList());
    }
}
