package com.flexbank.ws.repository;

import com.flexbank.ws.entity.CustomerPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPhoneNumberRepository extends
        JpaRepository<CustomerPhoneNumber, Integer> {

    CustomerPhoneNumber findByPhoneNumber(String phoneNumber);
}
