package com.PaymentService.DTOs;

import lombok.Data;

@Data
public class CompanyDTO {
    private String companyId;
    private String companyName;
    private String address;
    private String city;
    private double latitude;
    private double longitude;
    private String phone;
    private String status;
}

