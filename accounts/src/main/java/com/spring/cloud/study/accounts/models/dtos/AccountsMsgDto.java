package com.spring.cloud.study.accounts.models.dtos;

public record AccountsMsgDto(
        Long accountNumber,
        String name,
        String email,
        String mobileNumber
) {

}
