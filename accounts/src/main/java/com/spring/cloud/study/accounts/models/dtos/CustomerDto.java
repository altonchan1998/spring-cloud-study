package com.spring.cloud.study.accounts.models.dtos;

import com.spring.cloud.study.accounts.constants.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Size(min = 5, max = 30, message = ValidationMessages.LENGTH_BETWEEN + "5 and 30")
    @Schema(description = "Name of the customer", example = "Alton Chan")
    private String name;

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Email(message = ValidationMessages.INVALID_VALUE)
    @Schema(description = "Email address of the customer", example = "altonchan1998@gmail.com")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{8})", message = ValidationMessages.EIGHT_DIGITS)
    @Schema(description = "Mobile Number of the customer", example = "93454323")
    private String mobileNumber;

    @Schema(description = "Account details of the Customer")
    private AccountsDto accountsDto;
}
