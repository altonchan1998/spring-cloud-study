package com.spring.cloud.study.loans.services.impl;


import com.spring.cloud.study.loans.exceptions.ResourceExistsException;
import com.spring.cloud.study.loans.exceptions.ResourceNotFoundException;
import com.spring.cloud.study.loans.mappers.LoansMapper;
import com.spring.cloud.study.loans.models.dtos.LoansDto;
import com.spring.cloud.study.loans.models.entities.Loans;
import com.spring.cloud.study.loans.models.enums.LoanType;
import com.spring.cloud.study.loans.repositories.LoansRepository;
import com.spring.cloud.study.loans.services.ILoansService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoansServiceImpl implements ILoansService {

    @Autowired
    LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans= loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new ResourceExistsException("Loan", "mobileNumber " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanType.HOME_LOAN);
        newLoan.setTotalLoan(1_00_000);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(1_00_000);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
