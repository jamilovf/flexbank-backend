package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.CustomerPhoneNumberConverter;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.LoanRequestNotificationDto;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import com.flexbank.ws.entity.LoanNotification;
import com.flexbank.ws.entity.LoanRequest;
import com.flexbank.ws.entity.LoanStatus;
import com.flexbank.ws.repository.CustomerPhoneNumberRepository;
import com.flexbank.ws.repository.LoanNotificationRepository;
import com.flexbank.ws.repository.LoanRequestRepository;
import com.flexbank.ws.service.inter.AdminService;
import com.flexbank.ws.util.LoanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CustomerPhoneNumberRepository customerPhoneNumberRepository;
    private final CustomerPhoneNumberConverter customerPhoneNumberConverter;
    private final LoanRequestRepository loanRequestRepository;
    private final LoanNotificationRepository loanNotificationRepository;

    @Override
    public void registerCustomerDetails(CustomerPhoneNumberDto customerPhoneNumberDto) {
        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberConverter.dtoToEntity(customerPhoneNumberDto);

        customerPhoneNumberRepository.save(customerPhoneNumber);
    }

    @Override
    public List<LoanRequestNotificationDto> getAllLoanRequestNotifications() {

        List<LoanRequestNotificationDto> loanRequestNotificationDtos =
                loanRequestRepository.getAllLoanRequestNotifications();

        return loanRequestNotificationDtos;
    }

    @Override
    public void approveLoanRequest(Integer loanRequestId) {

       LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId).get();

       loanRequest.setStatus(LoanStatus.APPROVED);
       loanRequestRepository.save(loanRequest);

       double totalAmount = LoanUtils.calculateTotalRepaymentAmount(
               loanRequest.getAmount(), loanRequest.getType().getRate() ,loanRequest.getPeriod());

       List<LoanNotification> loanNotifications = new ArrayList<>();
       LocalDate paymentDate = LocalDate.now();

       for(int i = 0; i < loanRequest.getPeriod(); i++){
           paymentDate = paymentDate.plusMonths(1L);
           LoanNotification loanNotification =
                    LoanNotification.builder()
                            .interestRate((int)loanRequest.getType().getRate())
                            .amount(totalAmount / loanRequest.getPeriod())
                            .dueTo(paymentDate)
                            .type(loanRequest.getType())
                            .customerId(loanRequest.getCustomerId())
                            .build();

           loanNotifications.add(loanNotification);
       }
       loanNotificationRepository.saveAll(loanNotifications);
    }

    @Override
    public void declineLoanRequest(Integer loanRequestId) {

        loanRequestRepository.findById(loanRequestId)
                .ifPresent(loanRequest -> {
                    loanRequest.setStatus(LoanStatus.DECLINED);
                    loanRequestRepository.save(loanRequest);
                });
    }

}
