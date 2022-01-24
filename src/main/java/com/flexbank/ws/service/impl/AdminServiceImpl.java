package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.CustomerPhoneNumberConverter;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.LoanRequestNotificationDto;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import com.flexbank.ws.repository.CustomerPhoneNumberRepository;
import com.flexbank.ws.repository.LoanRequestRepository;
import com.flexbank.ws.service.inter.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final CustomerPhoneNumberRepository customerPhoneNumberRepository;
    private final CustomerPhoneNumberConverter customerPhoneNumberConverter;
    private final LoanRequestRepository loanRequestRepository;


    @Autowired
    public AdminServiceImpl(CustomerPhoneNumberRepository customerPhoneNumberRepository,
                            CustomerPhoneNumberConverter customerPhoneNumberConverter,
                            LoanRequestRepository loanRequestRepository) {
        this.customerPhoneNumberRepository = customerPhoneNumberRepository;
        this.customerPhoneNumberConverter = customerPhoneNumberConverter;
        this.loanRequestRepository = loanRequestRepository;
    }

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

}
