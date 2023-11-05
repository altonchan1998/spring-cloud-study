package com.spring.cloud.study.cards.services;

import com.spring.cloud.study.cards.models.dtos.CardsDto;

public interface ICardsService {
    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    boolean updateCard(CardsDto cardsDto);

    boolean deleteCard(String mobileNumber);

}
