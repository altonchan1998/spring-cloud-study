package com.spring.cloud.study.cards.services.impl;


import com.spring.cloud.study.cards.exceptions.ResourceNotFoundException;
import com.spring.cloud.study.cards.mappers.CardsMapper;
import com.spring.cloud.study.cards.models.dtos.CardsDto;
import com.spring.cloud.study.cards.models.entities.Cards;
import com.spring.cloud.study.cards.models.enums.CardType;
import com.spring.cloud.study.cards.repositories.CardsRepository;
import com.spring.cloud.study.cards.services.ICardsService;
import jakarta.persistence.EntityExistsException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    @Autowired
    CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards= cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new EntityExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardType.CREDIT_CARD);
        newCard.setTotalLimit(1_00_000);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(1_00_000);
        return newCard;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardsDto(cards, new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return  true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}
