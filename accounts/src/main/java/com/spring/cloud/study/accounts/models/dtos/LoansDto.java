package com.spring.cloud.study.accounts.models.dtos;

import com.spring.cloud.study.accounts.constants.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Loans",
        description = "Schema to hold Loan information"
)
@Data
public class LoansDto {

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Pattern(regexp="(^$|[0-9]{8})", message = ValidationMessages.EIGHT_DIGITS)
    @Schema(description = "Mobile Number of Customer", example = "4365327698")
    private String mobileNumber;

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Pattern(regexp="(^$|[0-9]{12})",message = ValidationMessages.TWELVE_DIGITS)
    @Schema(description = "Loan Number of the customer", example = "548732457654")
    private String loanNumber;

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Schema(description = "Type of the loan", example = "Home Loan")
    private String loanType;

    @Positive(message = ValidationMessages.POSITIVE_VALUE)
    @Schema(description = "Total loan amount", example = "100000")
    private int totalLoan;

    @PositiveOrZero(message = ValidationMessages.POSITIVE_OR_ZERO_VALUE)
    @Schema(description = "Total loan amount paid", example = "1000")
    private int amountPaid;

    @PositiveOrZero(message = ValidationMessages.POSITIVE_OR_ZERO_VALUE)
    @Schema(description = "Total outstanding amount against a loan", example = "99000")
    private int outstandingAmount;

}
