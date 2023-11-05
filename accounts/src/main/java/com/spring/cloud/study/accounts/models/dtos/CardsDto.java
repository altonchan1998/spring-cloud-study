package com.spring.cloud.study.accounts.models.dtos;

import com.spring.cloud.study.accounts.constants.ValidationMessages;
import com.spring.cloud.study.accounts.models.enums.CardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Cards",
        description = "Schema to hold Card information"
)
@Data
public class CardsDto {

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Pattern(regexp = "(^$|[0-9]{8})", message = ValidationMessages.EIGHT_DIGITS)
    @Schema(description = "Mobile Number of Customer", example = "43544376")
    private String mobileNumber;

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Pattern(regexp = "(^$|[0-9]{12})", message = ValidationMessages.TWELVE_DIGITS)
    @Schema(description = "Card Number of the customer", example = "100646930341")
    private String cardNumber;

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Schema(description = "Type of the card", example = "Credit Card")
    private CardType cardType;

    @Positive(message = ValidationMessages.POSITIVE_VALUE)
    @Schema(description = "Total amount limit available against a card", example = "100000")
    private int totalLimit;

    @PositiveOrZero(message = ValidationMessages.POSITIVE_OR_ZERO_VALUE)
    @Schema(description = "Total amount used by a Customer", example = "1000")
    private int amountUsed;

    @PositiveOrZero(message = ValidationMessages.POSITIVE_OR_ZERO_VALUE)
    @Schema(description = "Total available amount against a card", example = "90000")
    private int availableAmount;

}
