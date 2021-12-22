package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.CardConverter;
import com.flexbank.ws.dto.CardDto;
import com.flexbank.ws.entity.Card;
import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.repository.CardRepository;
import com.flexbank.ws.repository.CustomerRepository;
import com.flexbank.ws.service.inter.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    private final CardConverter cardConverter;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, CustomerRepository customerRepository, CardConverter cardConverter) {
        this.cardRepository = cardRepository;
        this.customerRepository = customerRepository;
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
    public void blockCard(Integer id) {

        Optional<Card> cardOptional = cardRepository.findById(id);

        cardOptional.ifPresent(card -> {
           if(card.getIsExpired()){
               throw new RuntimeException("Expired card cannot be blocked!");
           }
           if(card.getIsBlocked()){
               throw new RuntimeException("Card is already blocked!");
           }

           card.setIsBlocked(true);
           cardRepository.save(card);
        });
    }
}
