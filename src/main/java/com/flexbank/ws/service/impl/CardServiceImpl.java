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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;
    private final BankDetailsConfiguration bankDetailsConfiguration;

    private final CardConverter cardConverter;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository,
                           CustomerRepository customerRepository,
                           BankDetailsConfiguration bankDetailsConfiguration,
                           CardConverter cardConverter) {
        this.cardRepository = cardRepository;
        this.customerRepository = customerRepository;
        this.bankDetailsConfiguration = bankDetailsConfiguration;
        this.cardConverter = cardConverter;
    }

    @Override
    public List<CardDto> findAllByCustomerId(Integer customerId) {

        List<Card> cards = cardRepository.findAllByCustomerId(customerId);
        Optional<Customer> customer = customerRepository.findById(customerId);

        List<CardDto> cardDtos =
                cards.stream()
                     .map(card -> cardConverter.entityToDto(card))
                     .collect(Collectors.toList());

        cardDtos.stream()
                .forEach(cardDto ->
                        cardDto.setCustomerName(customer.get().getFirstName() + " "
                                + customer.get().getLastName()));

        return cardDtos;
    }

    @Override
    public void blockCard(Integer id) throws Exception{

        Card card = cardRepository.findById(id).get();

           if(card.getIsExpired()){
               throw new BadRequestException(ErrorMessage.EXPIRED_CARD_BLOCK.getErrorMessage());
           }
           if(card.getIsBlocked()){
               throw new BadRequestException(ErrorMessage.CARD_ALREADY_BLOCKED.getErrorMessage());
           }

           card.setIsBlocked(true);
           cardRepository.save(card);
    }

    @Override
    public void createCard(CardOrderRequest cardOrderRequest) {

        CardType cardType = cardOrderRequest.getType().equals("PREMIUM")
                ?  CardType.PREMIUM : CardType.STANDARD;

       String accountNumber =
               customerRepository.findById(cardOrderRequest.getCustomerId())
                       .get().getAccountNumber();

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
}
