package com.UserService.service;

import com.UserService.client.CustomerClient;
import com.UserService.client.RentalCompanyClient;
import com.UserService.dto.CustomerDto;
import com.UserService.dto.RentalCompanyDto;
import com.UserService.dto.UserDto;
import com.UserService.dto.UserRegistrationRequest;
import com.UserService.entity.Role;
import com.UserService.entity.User;
import com.UserService.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RentalCompanyClient rentalCompanyClient;
    @Autowired
    private CustomerClient customerClient;


    public ResponseEntity<String> registerUser(UserRegistrationRequest request) {
        UserDto userDto = request.getUser();
        RentalCompanyDto rentalCompanyDto = request.getRentalCompany();
        CustomerDto customerDto = request.getCustomer();

        // Check if email already exists
        if (repository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        // Save User
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();

        repository.save(user);

        // Role-based handling
        if (user.getRole() == Role.RENTAL_COMPANY) {
            if (rentalCompanyDto != null) {
                rentalCompanyDto.setUserId(user.getId());
                rentalCompanyDto.setCompanyId(user.getId());

                try {
                    rentalCompanyClient.registerRentalCompany(rentalCompanyDto);
                } catch (Exception e) {
                    System.err.println("Failed to register rental company: " + e.getMessage());
                }
            }else {
                System.err.println("RentalCompanyDto is null for RENTAL_COMPANY role");
            }
        } else if (user.getRole() == Role.CUSTOMER) {
            if (customerDto != null) {
                customerDto.setUserId(user.getId());
                customerDto.setCustomerId(user.getId());

                try {
                    customerClient.registercustomer(customerDto);
                } catch (Exception e) {
                    System.err.println("Failed to register customer: " + e.getMessage());
                }
            } else {
                System.err.println("CustomerDto is null for CUSTOMER role");
            }
        }

        return ResponseEntity.ok("User registered successfully");
    }


    public String generateToken(String email) {
        return jwtService.generateToken(email);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}
