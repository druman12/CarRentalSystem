package com.RentalCompaniesService.service;

import com.RentalCompaniesService.model.RentalCompany;

import java.util.List;

public interface RentalCompanyService {
    RentalCompany createRentalCompany(RentalCompany rentalCompany);

    List<RentalCompany> getAllRentalCompanies();

    RentalCompany getRentalCompanyById(Integer id);

    RentalCompany updateRentalCompany(Integer id, RentalCompany updatedCompany);

    void deleteRentalCompany(Integer id);

    List<RentalCompany> findRentalCompaniesByCity(String city);

    void activateRentalCompany(Integer id);

    void deactivateRentalCompany(Integer id);

}
