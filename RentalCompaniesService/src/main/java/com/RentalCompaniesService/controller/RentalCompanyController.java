package com.RentalCompaniesService.controller;

import com.RentalCompaniesService.model.RentalCompany;
import com.RentalCompaniesService.service.RentalCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rental-company")
public class RentalCompanyController {

    private final RentalCompanyService rentalCompanyService;

    @Autowired
    public RentalCompanyController(RentalCompanyService rentalCompanyService) {
        this.rentalCompanyService = rentalCompanyService;
    }

    // Create a new rental company
    @PostMapping
    public ResponseEntity<RentalCompany> createRentalCompany(@RequestBody RentalCompany rentalCompany) {
        return ResponseEntity.ok(rentalCompanyService.createRentalCompany(rentalCompany));
    }

    // Get all rental companies
    @GetMapping
    public ResponseEntity<List<RentalCompany>> getAllRentalCompanies() {
        return ResponseEntity.ok(rentalCompanyService.getAllRentalCompanies());
    }

    // Get a rental company by ID
    @GetMapping("/{id}")
    public ResponseEntity<RentalCompany> getRentalCompanyById(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalCompanyService.getRentalCompanyById(id));
    }

    // Update a rental company
    @PutMapping("/{id}")
    public ResponseEntity<RentalCompany> updateRentalCompany(@PathVariable Integer id, @RequestBody RentalCompany rentalCompany) {
        return ResponseEntity.ok(rentalCompanyService.updateRentalCompany(id, rentalCompany));
    }

    // Delete (soft delete / deactivate) a rental company
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalCompany(@PathVariable Integer id) {
        rentalCompanyService.deleteRentalCompany(id);
        return ResponseEntity.noContent().build();
    }

    // Search rental companies by city
    @GetMapping("/city/{city}")
    public ResponseEntity<List<RentalCompany>> findByCity(@PathVariable String city) {
        return ResponseEntity.ok(rentalCompanyService.findRentalCompaniesByCity(city));
    }

    // Activate a rental company
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateRentalCompany(@PathVariable Integer id) {
        rentalCompanyService.activateRentalCompany(id);
        return ResponseEntity.noContent().build();
    }

    // Deactivate a rental company
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateRentalCompany(@PathVariable Integer id) {
        rentalCompanyService.deactivateRentalCompany(id);
        return ResponseEntity.noContent().build();
    }
}
