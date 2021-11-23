package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.CardConverter;
import com.flexbank.ws.dto.CardDto;
import com.flexbank.ws.entity.Card;
import com.flexbank.ws.repository.CardRepository;
import com.flexbank.ws.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CardConverter cardConverter;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, CardConverter cardConverter) {
        this.cardRepository = cardRepository;
        this.cardConverter = cardConverter;
    }

    @Override
    public List<CardDto> findAllByCustomerId(Integer customerId) {

        List<Card> cards = cardRepository.findAllByCustomerId(customerId);

        List<CardDto> cardDtos =
                cards.stream()
                     .map(card -> cardConverter.entityToDto(card))
                     .collect(Collectors.toList());

        return cardDtos;
    }
}
