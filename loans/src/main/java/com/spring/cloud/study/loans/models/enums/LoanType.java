package com.spring.cloud.study.loans.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum LoanType {
    HOME_LOAN("Home Loan");

    public final String code;

    LoanType(String code) {
        this.code = code;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static LoanType decode(String code) {
        return Stream.of(LoanType.values())
                .filter(targetEnum -> targetEnum.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
