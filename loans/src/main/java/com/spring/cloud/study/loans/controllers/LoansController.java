package com.spring.cloud.study.loans.controllers;

import com.spring.cloud.study.loans.constants.ValidationMessages;
import com.spring.cloud.study.loans.models.dtos.LoansDto;
import com.spring.cloud.study.loans.services.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Loans",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class LoansController {

    @Autowired
    ILoansService iLoansService;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside EazyBank"
    )
    @ApiResponses
    @PostMapping("/loan")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createLoan(
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{8})",message = ValidationMessages.EIGHT_DIGITS)
            String mobileNumber
    ) {
        iLoansService.createLoan(mobileNumber);
    }

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch loan details based on a mobile number"
    )
    @ApiResponses
    @GetMapping("/loan/details")
    public LoansDto fetchLoanDetails(
            @RequestHeader("correlation-id")
            String correlationId,
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{8})",message = ValidationMessages.EIGHT_DIGITS)
            String mobileNumber
    ) {
        log.debug("fetchLoanDetails method start");
        LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);
        log.debug("fetchLoanDetails method end");
        return loansDto;
    }

    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses
    @PutMapping("/loan/details")
    public void updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
        iLoansService.updateLoan(loansDto);
    }

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses
    @DeleteMapping("/loan/details")
    public void deleteLoanDetails(
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{8})",message = ValidationMessages.EIGHT_DIGITS)
            String mobileNumber
    ) {
        iLoansService.deleteLoan(mobileNumber);
    }
}
