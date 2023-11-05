package com.spring.cloud.study.cards.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum CardType {
    CREDIT_CARD("Credit Card");

    public final String code;

    CardType(String code) {
        this.code = code;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CardType decode(String code) {
        return Stream.of(CardType.values())
                .filter(targetEnum -> targetEnum.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
