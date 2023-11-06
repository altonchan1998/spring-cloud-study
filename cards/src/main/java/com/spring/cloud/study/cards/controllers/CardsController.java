package com.spring.cloud.study.cards.controllers;

import com.spring.cloud.study.cards.constants.ValidationMessages;
import com.spring.cloud.study.cards.models.dtos.CardsContactInfoDto;
import com.spring.cloud.study.cards.models.dtos.CardsDto;
import com.spring.cloud.study.cards.services.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@Tag(
        name = "CRUD REST APIs for Cards",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class CardsController {

    @Autowired
    ICardsService iCardsService;

    @Autowired
    CardsContactInfoDto cardsContactInfoDto;

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card inside EazyBank"
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiResponses
    @PostMapping("/card")
    public void createCard(
            @Valid @RequestParam
            @Pattern(regexp="(^$|[0-9]{8})",message = ValidationMessages.EIGHT_DIGITS)
            String mobileNumber
    ) {
        iCardsService.createCard(mobileNumber);
    }

    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a mobile number"
    )
    @ApiResponses
    @GetMapping("/fetch")
    public CardsDto fetchCardDetails(
            @RequestHeader("correlation-id") String correlationId,
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{8})",message = ValidationMessages.EIGHT_DIGITS)
            String mobileNumber
    ) {
        log.debug("fetchCardDetails method start");
        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
        log.debug("fetchCardDetails method end");
        return cardsDto;
    }

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
    )
    @ApiResponses
    @PutMapping("/card/details")
    public void updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        iCardsService.updateCard(cardsDto);
    }

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a mobile number"
    )
    @ApiResponses
    @DeleteMapping("/card/details")
    public void deleteCardDetails(
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{8})",message = ValidationMessages.EIGHT_DIGITS)
            String mobileNumber
    ) {
        iCardsService.deleteCard(mobileNumber);
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses
    @GetMapping("/contact-info")
    public CardsContactInfoDto getContactInfo() {
        return cardsContactInfoDto;
    }
}
