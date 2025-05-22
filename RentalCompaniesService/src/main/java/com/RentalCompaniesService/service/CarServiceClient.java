package com.RentalCompaniesService.service;

import com.RentalCompaniesService.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "car-service")
public interface CarServiceClient {

    @GetMapping("/api/cars/companyid/{companyId}")
    List<Car> getCarsofCompany(@PathVariable("companyId") Integer companyId);
}

