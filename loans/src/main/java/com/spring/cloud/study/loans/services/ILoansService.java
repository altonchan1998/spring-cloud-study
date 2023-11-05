package com.spring.cloud.study.loans.services;

import com.spring.cloud.study.loans.models.dtos.LoansDto;

public interface ILoansService {
    void createLoan(String mobileNumber);

    LoansDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(String mobileNumber);

}
