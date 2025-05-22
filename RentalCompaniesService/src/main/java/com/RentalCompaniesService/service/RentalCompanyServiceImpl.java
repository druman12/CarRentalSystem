package com.RentalCompaniesService.service;

import com.RentalCompaniesService.Repo.RentalCompanyRepo;
import com.RentalCompaniesService.model.Car;
import com.RentalCompaniesService.model.RentalCompany;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalCompanyServiceImpl implements RentalCompanyService {

    @Autowired
    private RentalCompanyRepo rentalCompanyRepository;

    @Autowired
    private CarServiceClient carServiceClient;

    @Autowired
    private RentalCompanyResilientService resilientService;

    @Override
    public RentalCompany getRentalCompanyById(Integer id) {
        RentalCompany rentalCompany = rentalCompanyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental Company not found with ID: " + id));

        rentalCompany.setCars(resilientService.getCarsForCompany(rentalCompany.getCompanyId()));
        return rentalCompany;
    }
    @Override
    public RentalCompany createRentalCompany(RentalCompany rentalCompany) {
        if (rentalCompany.getUserId() == null || rentalCompany.getUserId().describeConstable().isEmpty()) {
            throw new IllegalArgumentException("User ID must be set for rental company");
        }
        rentalCompany.setCompanyId(Integer.valueOf(rentalCompany.getUserId()));
        return rentalCompanyRepository.save(rentalCompany);
    }

    @Override
    public List<RentalCompany> getAllRentalCompanies() {
        List<RentalCompany> companies = rentalCompanyRepository.findAll();
        return companies.stream().map(rentalCompany -> {
            rentalCompany.setCars(resilientService.getCarsForCompany(rentalCompany.getCompanyId()));
            return rentalCompany;
        }).collect(Collectors.toList());
    }


    @Override
    public RentalCompany updateRentalCompany(Integer id, RentalCompany updatedCompany) {
        RentalCompany existing = getRentalCompanyById(id);

        existing.setCompanyName(updatedCompany.getCompanyName());
        existing.setAddress(updatedCompany.getAddress());
        existing.setCity(updatedCompany.getCity());
        existing.setLatitude(updatedCompany.getLatitude());
        existing.setLongitude(updatedCompany.getLongitude());
        existing.setPhoneNumber(updatedCompany.getPhoneNumber());
        existing.setStatus(updatedCompany.getStatus());

        return rentalCompanyRepository.save(existing);
    }

    @Override
    public void deleteRentalCompany(Integer id) {
        RentalCompany existing = getRentalCompanyById(id);
        existing.setStatus("inactive"); // Soft delete
        rentalCompanyRepository.save(existing);
    }

    @Override
    public List<RentalCompany> findRentalCompaniesByCity(String city) {
        List<RentalCompany> companies = rentalCompanyRepository.findByCityIgnoreCase(city);
        return companies.stream().map(rentalCompany -> {
            rentalCompany.setCars(resilientService.getCarsForCompany(rentalCompany.getCompanyId()));
            return rentalCompany;
        }).collect(Collectors.toList());
    }


    public List<RentalCompany> fallbackFindRentalCompaniesByCity(String city, Throwable t) {
        List<RentalCompany> companies = rentalCompanyRepository.findByCityIgnoreCase(city);
        companies.forEach(company -> company.setCars(Collections.emptyList()));
        return companies;
    }

    @Override
    public void activateRentalCompany(Integer id) {
        RentalCompany existing = getRentalCompanyById(id);
        existing.setStatus("active");
        rentalCompanyRepository.save(existing);
    }

    @Override
    public void deactivateRentalCompany(Integer id) {
        RentalCompany existing = getRentalCompanyById(id);
        existing.setStatus("inactive");
        rentalCompanyRepository.save(existing);
    }
}
