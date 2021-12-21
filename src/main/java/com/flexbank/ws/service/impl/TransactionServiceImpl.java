package com.flexbank.ws.service.impl;

import com.flexbank.ws.client.ibanapi.IbanApiClient;
import com.flexbank.ws.configuration.rabbitmq.RabbitMqConfiguration;
import com.flexbank.ws.converter.TransactionConverter;
import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.dto.request.ExternalTransferRequest;
import com.flexbank.ws.dto.request.InternalTransferRequest;
import com.flexbank.ws.entity.Card;
import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.entity.Transaction;
import com.flexbank.ws.entity.TransactionType;
import com.flexbank.ws.repository.CardRepository;
import com.flexbank.ws.repository.CustomerRepository;
import com.flexbank.ws.repository.TransactionRepository;
import com.flexbank.ws.service.inter.TransactionService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final CardRepository cardRepository;
    private final IbanApiClient ibanApiClient;

    private final DirectExchange exchange;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqConfiguration rabbitMqConfiguration;

    private final TransactionConverter transactionConverter;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CustomerRepository customerRepository,
                                  CardRepository cardRepository,
                                  IbanApiClient ibanApiClient,
                                  DirectExchange exchange,
                                  RabbitTemplate rabbitTemplate,
                                  RabbitMqConfiguration rabbitMqConfiguration,
                                  TransactionConverter transactionConverter) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.cardRepository = cardRepository;
        this.ibanApiClient = ibanApiClient;
        this.exchange = exchange;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMqConfiguration = rabbitMqConfiguration;
        this.transactionConverter = transactionConverter;
    }

    @Override
    public List<TransactionDto> findAllByCustomerId(Integer customerId) {

        List<Transaction> transactions = transactionRepository.findAllByCustomerId(customerId);

        List<TransactionDto> transactionDtos =
                transactions.stream()
                .map(transaction -> transactionConverter.entityToDto(transaction))
                .collect(Collectors.toList());

        return transactionDtos;
    }

    @Override
    @Transactional
    public void transferInternal(InternalTransferRequest internalTransferRequest) {

        Card senderCard = cardRepository
                .findByCardNumber(internalTransferRequest.getChosenCard());
        Card recipientCard = cardRepository
                .findByCardNumber(internalTransferRequest.getRecipientCardNumber());

        if(recipientCard == null){
            throw new RuntimeException("There is no card with this number!");
        }

        Customer recipientCustomer = customerRepository
                .findById(recipientCard.getCustomerId()).get();

        if(!internalTransferRequest.getFirstName().equals(recipientCustomer.getFirstName()) ||
                !internalTransferRequest.getLastName().equals(recipientCustomer.getLastName())){
            throw new RuntimeException("Recipient first or last name is wrong!");
        }

        Double amount = internalTransferRequest.getAmount();

        if(senderCard.getBalance() < amount){
            throw new RuntimeException("Insufficient balance!");
        }

        senderCard.setBalance(senderCard.getBalance() - amount);
        recipientCard.setBalance(recipientCard.getBalance() + amount);

        cardRepository.save(senderCard);
        cardRepository.save(recipientCard);

        LocalDate transactionDate = LocalDate.now();
        LocalTime transactionTime = LocalTime.now();

        Transaction senderTransaction = Transaction.builder()
                .amount(amount * -1)
                .type(TransactionType.INTERNAL_TRANSFER)
                .createdAtDate(transactionDate)
                .createdAtTime(transactionTime)
                .customerId(senderCard.getCustomerId())
                .build();

        Transaction recipientTransaction = Transaction.builder()
                .amount(amount)
                .type(TransactionType.INTERNAL_TRANSFER)
                .createdAtDate(transactionDate)
                .createdAtTime(transactionTime)
                .customerId(recipientCard.getCustomerId())
                .build();

        transactionRepository.save(senderTransaction);
        transactionRepository.save(recipientTransaction);
    }

    @Override
    public void transferExternal(ExternalTransferRequest externalTransferRequest) {

        rabbitTemplate.convertAndSend(exchange.getName(),
                rabbitMqConfiguration.getExternalTransferRoutingKey(),
                externalTransferRequest);

    }
}
