package com.RentalCompaniesService.service;

import com.RentalCompaniesService.Repo.RentalCompanyRepo;
import com.RentalCompaniesService.model.Car;
import com.RentalCompaniesService.model.RentalCompany;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RentalCompanyResilientService {

    @Autowired
    private CarServiceClient carServiceClient;

    @Autowired
    private RentalCompanyRepo rentalCompanyRepo;

    @CircuitBreaker(name = "rentalCompanyCB", fallbackMethod = "fallbackGetCars")
    public List<Car> getCarsForCompany(Integer companyId) {
        return carServiceClient.getCarsofCompany(companyId);
    }

    public List<Car> fallbackGetCars(Integer companyId, Throwable t) {
        return Collections.emptyList();
    }

}

