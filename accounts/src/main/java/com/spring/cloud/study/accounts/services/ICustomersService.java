package com.spring.cloud.study.accounts.services;


import com.spring.cloud.study.accounts.models.dtos.CustomerDetailsDto;

public interface ICustomersService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
