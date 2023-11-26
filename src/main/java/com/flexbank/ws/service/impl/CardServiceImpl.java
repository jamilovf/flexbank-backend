package com.flexbank.ws.service.impl;

import com.flexbank.ws.configuration.bankdetails.BankDetailsConfiguration;
import com.flexbank.ws.converter.CardConverter;
import com.flexbank.ws.dto.CardDto;
import com.flexbank.ws.dto.request.CardOrderRequest;
import com.flexbank.ws.entity.Card;
import com.flexbank.ws.entity.CardType;
import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.exception.BadRequestException;
import com.flexbank.ws.exception.ErrorMessage;
import com.flexbank.ws.repository.CardRepository;
import com.flexbank.ws.repository.CustomerRepository;
import com.flexbank.ws.service.inter.CardService;
import com.flexbank.ws.util.CardUtils;
import com.flexbank.ws.util.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;
    private final BankDetailsConfiguration bankDetailsConfiguration;

    private final CardConverter cardConverter;

    @Override
    public List<CardDto> findAllByCustomerId(Integer customerId) {

        List<Card> cards = cardRepository.findAllByCustomerId(customerId);
        Optional<Customer> customer = customerRepository.findById(customerId);

        List<CardDto> cardDtos = new ArrayList<>();
        if(cards != null) {
             cardDtos = cards.stream()
                            .map(card -> cardConverter.entityToDto(card))
                            .collect(Collectors.toList());

            cardDtos.stream()
                    .forEach(cardDto ->
                            cardDto.setCustomerName(customer.get().getFirstName() + " "
                                    + customer.get().getLastName()));
        }

        return cardDtos;
    }

    @Override
    public CardDto blockCard(Integer id) throws Exception{

        Card card = cardRepository.findById(id).get();

           if(card.getIsExpired()){
               throw new BadRequestException(ErrorMessage.EXPIRED_CARD_BLOCK.getErrorMessage());
           }
           if(card.getIsBlocked()){
               throw new BadRequestException(ErrorMessage.CARD_ALREADY_BLOCKED.getErrorMessage());
           }

           card.setIsBlocked(true);
           Card blockedCard = cardRepository.save(card);

           return cardConverter.entityToDto(blockedCard);
    }

    @Override
    public void createCard(CardOrderRequest cardOrderRequest) {

        CardType cardType = cardOrderRequest.getType().equals("PREMIUM")
                ?  CardType.PREMIUM : CardType.STANDARD;

       String accountNumber = CustomerUtils.generateAccountNumber();

        String cardNumber =
                CardUtils.generateCardNumber(bankDetailsConfiguration.getMII(),
                        bankDetailsConfiguration.getBIN(),accountNumber);

        Card card = Card.builder()
                .cardType(cardType)
                .cardNumber(cardNumber)
                .expiryDate(LocalDate.now().plusYears(3))
                .balance(0.0)
                .customerId(cardOrderRequest.getCustomerId())
                .isBlocked(false)
                .isExpired(false)
                .build();

        cardRepository.save(card);
    }

    @Override
    public CardDto unblockCard(Integer id) throws BadRequestException {
        Card card = cardRepository.findById(id).get();

        if(card.getIsExpired()){
            throw new BadRequestException(ErrorMessage.EXPIRED_CARD_BLOCK.getErrorMessage());
        }
        if(!card.getIsBlocked()){
            throw new BadRequestException(ErrorMessage.CARD_ALREADY_UNBLOCKED.getErrorMessage());
        }

        card.setIsBlocked(false);
        Card unblockedCard = cardRepository.save(card);

        return cardConverter.entityToDto(unblockedCard);
    }
}
