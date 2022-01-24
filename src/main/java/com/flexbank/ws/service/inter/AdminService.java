package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.LoanRequestNotificationDto;

import java.util.List;

public interface AdminService {

    void registerCustomerDetails(CustomerPhoneNumberDto customerPhoneNumberDto);

    List<LoanRequestNotificationDto> getAllLoanRequestNotifications();

    void approveLoanRequest(Integer loanRequestId);
}
