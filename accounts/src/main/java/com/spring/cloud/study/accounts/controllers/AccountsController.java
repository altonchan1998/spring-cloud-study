package com.spring.cloud.study.accounts.controllers;

import com.spring.cloud.study.accounts.constants.ValidationMessages;
import com.spring.cloud.study.accounts.models.dtos.CustomerDto;
import com.spring.cloud.study.accounts.services.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CRUD REST APIs for Accounts",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE account details"
)
@RestController
@RequestMapping(path = "/api")
@Validated
@Slf4j
public class AccountsController {
    @Autowired
    private IAccountsService iAccountsService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer &  Account"
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/account")
    public void createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.createAccount(customerDto);
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"
    )
    @ApiResponses
    @GetMapping("/account/details")
    public CustomerDto fetchAccountDetails(
            @RequestParam @Pattern(regexp="(^$|[0-9]{8})", message = ValidationMessages.EIGHT_DIGITS)
            String mobileNumber
    ) {
        return iAccountsService.fetchAccount(mobileNumber);
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
    )
    @ApiResponses
    @PutMapping("/account/details")
    public void updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.updateAccount(customerDto);
    }

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses
    @DeleteMapping("/account/details")
    public void deleteAccountDetails(
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{8})",message = ValidationMessages.EIGHT_DIGITS)
            String mobileNumber
    ) {
        iAccountsService.deleteAccount(mobileNumber);
    }
}
