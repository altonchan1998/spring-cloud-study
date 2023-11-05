package com.spring.cloud.study.accounts.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;

public enum AccountType {
    SAVING("saving");

    public final String code;

    AccountType(String code) {
        this.code = code;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static AccountType decode(String code) {
        return Stream.of(AccountType.values())
                .filter(targetEnum -> targetEnum.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
