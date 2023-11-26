package com.flexbank.ws.service.impl;

import com.flexbank.ws.client.ibanapi.IbanApiClient;
import com.flexbank.ws.configuration.rabbitmq.RabbitMqConfiguration;
import com.flexbank.ws.configuration.security.SecurityContextService;
import com.flexbank.ws.converter.TransactionConverter;
import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.dto.request.ExternalTransferRequest;
import com.flexbank.ws.dto.request.InternalTransferRequest;
import com.flexbank.ws.entity.*;
import com.flexbank.ws.exception.ErrorMessage;
import com.flexbank.ws.exception.NotFoundException;
import com.flexbank.ws.repository.CardRepository;
import com.flexbank.ws.repository.CustomerRepository;
import com.flexbank.ws.repository.LoanNotificationRepository;
import com.flexbank.ws.repository.TransactionRepository;
import com.flexbank.ws.service.inter.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final CardRepository cardRepository;
    private final LoanNotificationRepository loanNotificationRepository;
    private final IbanApiClient ibanApiClient;

    private final DirectExchange exchange;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqConfiguration rabbitMqConfiguration;

    private final TransactionConverter transactionConverter;

    @Override
    public List<TransactionDto> findAllByCustomerId(int page, int limit) {

        Integer customerId = SecurityContextService.getCurrentCustomerId();

        if(page > 0) {
            page = page - 1;
        }

        Pageable pageable = PageRequest.of(page, limit, Sort.by("createdAtDate", "createdAtTime"));
        Page<Transaction> transactions = transactionRepository
                .findAllByCustomerId(customerId, pageable);

        List<TransactionDto> transactionDtos =
                transactions.stream()
                .map(transaction -> transactionConverter.entityToDto(transaction))
                .collect(Collectors.toList());

        int trPage = page;
        transactionDtos.forEach(transactionDto -> {
            transactionDto.setPage(trPage);
            transactionDto.setLimit(limit);
        });

        return transactionDtos;
    }

    @Override
    @Transactional
    public void transferInternal(InternalTransferRequest internalTransferRequest)
            throws Exception{

        Card senderCard = cardRepository
                .findById(internalTransferRequest.getChosenCardId()).get();
        Card recipientCard = cardRepository
                .findByCardNumber(internalTransferRequest.getRecipientCardNumber());

        if(recipientCard == null){
            throw new NotFoundException(ErrorMessage.WRONG_CARD_NUMBER.getErrorMessage());
        }

        Customer recipientCustomer = customerRepository
                .findById(recipientCard.getCustomerId()).get();

        if(!internalTransferRequest.getFirstName().equals(recipientCustomer.getFirstName()) ||
                !internalTransferRequest.getLastName().equals(recipientCustomer.getLastName())){
            throw new NotFoundException(ErrorMessage.RECIPIENT_NAME_ERROR.getErrorMessage());
        }

        Double amount = internalTransferRequest.getAmount();

        if(senderCard.getBalance() < amount){
            throw new Exception(ErrorMessage.INSUFFICIENT_BALANCE.getErrorMessage());
        }

        senderCard.setBalance(senderCard.getBalance() - amount);
        recipientCard.setBalance(recipientCard.getBalance() + amount);

        cardRepository.save(senderCard);
        cardRepository.save(recipientCard);

        LocalDate transactionDate = LocalDate.now();
        LocalTime transactionTime = LocalTime.now();

        Transaction senderTransaction = Transaction.builder()
                .amount(amount * -1)
                .type("Transfer")
                .description(TransactionType.INTERNAL_TRANSFER.getText())
                .createdAtDate(transactionDate)
                .createdAtTime(transactionTime)
                .customerId(senderCard.getCustomerId())
                .build();

        Transaction recipientTransaction = Transaction.builder()
                .amount(amount)
                .type("Transfer")
                .description(TransactionType.INTERNAL_TRANSFER.getText())
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

    @Override
    public Integer countPagesByCustomerId() {
        Integer customerId = SecurityContextService.getCurrentCustomerId();
        int count = transactionRepository.countByCustomerId(customerId);

        int pageCount =  (count % 10 == 0) ? (count / 10) : (count / 10 + 1);

        return pageCount;
    }

    @Override
    public List<TransactionDto> searchTransactionsByDateAndType(String from, String to,
                                                                String type1, String type2,
                                                                int page, int limit) {

        Integer customerId = SecurityContextService.getCurrentCustomerId();

        if(page > 0) {
            page = page - 1;
        }

        List<String> types = new ArrayList<>();
        types.add(type1);
        types.add(type2);

        String[] fromDateSplit =  from.split("-");
        String[] toDateSplit =  to.split("-");

        LocalDate fromDate = LocalDate.of(Integer.parseInt(fromDateSplit[0]),
                Integer.parseInt(fromDateSplit[1]),Integer.parseInt(fromDateSplit[2]));
        LocalDate toDate = LocalDate.of(Integer.parseInt(toDateSplit[0]),
                Integer.parseInt(toDateSplit[1]),Integer.parseInt(toDateSplit[2]));

        Pageable pageable = PageRequest.of(page, limit, Sort.by("createdAtDate", "createdAtTime"));
        Page<Transaction> transactions = transactionRepository
                .findAllByCreatedAtDateBetweenAndCustomerIdAndTypeIn(
                         fromDate, toDate, customerId, types, pageable);

        Integer count =
                transactionRepository
                        .countByCreatedAtDateBetweenAndCustomerIdAndTypeIn(
                                fromDate, toDate, customerId, types);

        int pageCount =  (count % limit == 0) ? (count / limit) : (count / limit + 1);

        List<TransactionDto> transactionDtos =
                transactions.stream()
                        .map(transaction -> transactionConverter.entityToDto(transaction))
                        .collect(Collectors.toList());

        int trPage = page;
        transactionDtos.forEach(transactionDto -> {
            transactionDto.setPage(trPage);
            transactionDto.setLimit(limit);
            transactionDto.setCount(pageCount);
        });

        return transactionDtos;
    }

    @Override
    @Transactional
    public void payLoan(Integer loanId, Integer cardId) throws Exception {

        LoanNotification loanNotification = loanNotificationRepository.findById(loanId).get();

        Card card = cardRepository.findById(cardId).get();

        if(card.getBalance() < loanNotification.getAmount()){
            throw new Exception(ErrorMessage.INSUFFICIENT_BALANCE.getErrorMessage());
        }

        card.setBalance(card.getBalance() - loanNotification.getAmount());
        loanNotification.setIsPaid(true);

        cardRepository.save(card);
        loanNotificationRepository.save(loanNotification);
    }
}
