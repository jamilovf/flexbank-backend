package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.CustomerPhoneNumberConverter;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import com.flexbank.ws.repository.CustomerPhoneNumberRepository;
import com.flexbank.ws.service.inter.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final CustomerPhoneNumberRepository customerPhoneNumberRepository;
    private final CustomerPhoneNumberConverter customerPhoneNumberConverter;


    @Autowired
    public AdminServiceImpl(CustomerPhoneNumberRepository customerPhoneNumberRepository,
                            CustomerPhoneNumberConverter customerPhoneNumberConverter) {
        this.customerPhoneNumberRepository = customerPhoneNumberRepository;
        this.customerPhoneNumberConverter = customerPhoneNumberConverter;
    }

    @Override
    public void registerCustomerDetails(CustomerPhoneNumberDto customerPhoneNumberDto) {
        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberConverter.dtoToEntity(customerPhoneNumberDto);

        customerPhoneNumberRepository.save(customerPhoneNumber);
    }

}
