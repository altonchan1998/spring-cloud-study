package com.spring.cloud.study.accounts.models.dtos;

import com.spring.cloud.study.accounts.constants.ValidationMessages;
import com.spring.cloud.study.accounts.models.enums.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Pattern(regexp="(^$|[0-9]{10})", message = ValidationMessages.TEN_DIGITS)
    @Schema(description = "Account Number", example = "3454433243")
    private Long accountNumber;

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Schema(description = "Account type", example = "SAVINGS")
    private AccountType accountType;

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Schema(description = "Branch address", example = "123 NewYork")
    private String branchAddress;
}
