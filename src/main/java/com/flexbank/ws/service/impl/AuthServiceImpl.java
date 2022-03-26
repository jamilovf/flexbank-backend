package com.flexbank.ws.service.impl;

import com.flexbank.ws.client.sms.SmsRequest;
import com.flexbank.ws.client.sms.SmsSender;
import com.flexbank.ws.converter.CustomerConverter;
import com.flexbank.ws.converter.CustomerPhoneNumberConverter;
import com.flexbank.ws.dto.CustomerDto;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import com.flexbank.ws.entity.Role;
import com.flexbank.ws.entity.RoleType;
import com.flexbank.ws.exception.BadRequestException;
import com.flexbank.ws.exception.ErrorMessage;
import com.flexbank.ws.repository.CustomerPhoneNumberRepository;
import com.flexbank.ws.repository.CustomerRepository;
import com.flexbank.ws.service.inter.AuthService;
import com.flexbank.ws.service.inter.CustomerPhoneNumberService;
import com.flexbank.ws.service.inter.CustomerService;
import com.flexbank.ws.service.inter.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomerPhoneNumberService customerPhoneNumberService;
    private final CustomerPhoneNumberRepository customerPhoneNumberRepository;
    private final SmsSender smsSender;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final CustomerPhoneNumberConverter customerPhoneNumberConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthServiceImpl(CustomerPhoneNumberService customerPhoneNumberService,
                           CustomerPhoneNumberRepository customerPhoneNumberRepository,
                           SmsSender smsSender,
                           CustomerService customerService,
                           CustomerRepository customerRepository,
                           CustomerConverter customerConverter,
                           CustomerPhoneNumberConverter customerPhoneNumberConverter,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           RoleRepository roleRepository) {
        this.customerPhoneNumberService = customerPhoneNumberService;
        this.customerPhoneNumberRepository = customerPhoneNumberRepository;
        this.smsSender = smsSender;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
        this.customerPhoneNumberConverter = customerPhoneNumberConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public CustomerPhoneNumberDto verifyPhoneNumber(String phoneNumber) throws Exception {
        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberService.findByPhoneNumber(phoneNumber);

        if(customerPhoneNumber.isRegistered()){
            throw new BadRequestException(ErrorMessage.CUSTOMER_ALREADY_REGISTERED.getErrorMessage());
        }

        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberConverter.entityToDto(customerPhoneNumber);

        SmsRequest smsRequest = new SmsRequest(phoneNumber,
                "Hello, " + customerPhoneNumberDto.getFirstName() + " " +
                        customerPhoneNumberDto.getLastName() + "! " +
                "Welcome to FlexBank!\n Your verification code: ");
        smsSender.sendSms(smsRequest);

        customerPhoneNumber = customerPhoneNumberService.findByPhoneNumber(phoneNumber);

        customerPhoneNumberDto = customerPhoneNumberConverter.entityToDto(customerPhoneNumber);

        return customerPhoneNumberDto;
    }

    @Override
    public CustomerPhoneNumberDto verifySmsCode(String phoneNumber, String smsCode) throws Exception {
        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberService.verifySmsCode(phoneNumber, smsCode);

        return customerPhoneNumberDto;
    }

    @Override
    public void signup(CustomerDto customerDto) throws Exception {
       customerService.findByEmail(customerDto.getEmail());

       if (!customerDto.getPassword().equals(customerDto.getPasswordConfirmation())){
           throw new BadRequestException(ErrorMessage.PASSWORDS_MUST_EQUAL.getErrorMessage());
       }

       Customer customer = customerConverter.dtoToEntity(customerDto);
       customer.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));
       customer.setPhoneNumber(customerDto.getPhoneNumber());

       CustomerPhoneNumber customerPhoneNumber =
               customerPhoneNumberRepository.findByPhoneNumber(customerDto.getPhoneNumber());
       customerPhoneNumber.setRegistered(true);
       customerPhoneNumber.setMessageCode(null);
       customerPhoneNumber.setMessageCodeAllowed(false);
       customerPhoneNumber.setSignupAllowed(false);

       customer.setFirstName(customerPhoneNumber.getFirstName());
       customer.setLastName(customerPhoneNumber.getLastName());
       customer.setBirthDate(customerPhoneNumber.getBirthDate());
       customer.setRoleId(RoleType.ROLE_CUSTOMER.getRoleId());

       customerPhoneNumberRepository.save(customerPhoneNumber);
       customerService.save(customer);
    }

    @Override
    public Customer getCustomer(String email) {
        Customer customer = customerRepository.findByEmail(email);

        if (customer == null)
            throw new UsernameNotFoundException(email);

        return customer;
    }

    @Override
    public CustomerPhoneNumberDto verifyPhoneNumberForPasswordReset(String phoneNumber) throws Exception {
        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberService.findByPhoneNumber(phoneNumber);

        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberConverter.entityToDto(customerPhoneNumber);

        SmsRequest smsRequest = new SmsRequest(phoneNumber,
                "Hello, " + customerPhoneNumberDto.getFirstName() + " " +
                        customerPhoneNumberDto.getLastName() + "! " +
                        "Welcome to FlexBank!\n Your verification code: ");
        smsSender.sendSmsForPasswordReset(smsRequest);

        customerPhoneNumber = customerPhoneNumberService.findByPhoneNumber(phoneNumber);

        customerPhoneNumberDto = customerPhoneNumberConverter.entityToDto(customerPhoneNumber);

        return customerPhoneNumberDto;
    }

    @Override
    public CustomerPhoneNumberDto verifySmsCodeForPasswordReset(
            String phoneNumber, String smsCode) throws Exception {
        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberService.verifySmsCodeForPasswordReset(phoneNumber, smsCode);

        return customerPhoneNumberDto;
    }

    @Override
    public void resetPassword(String email, String newPassword) throws Exception {
        Customer customer = customerRepository.findByEmail(email);

        if(customer == null){
            throw new UsernameNotFoundException(email);
        }

        customer.setPassword(bCryptPasswordEncoder.encode(newPassword));

        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberRepository.findByPhoneNumber(customer.getPhoneNumber());

        customerPhoneNumber.setResetPasswordMessageCode(null);
        customerPhoneNumber.setResetPasswordMessageCodeAllowed(false);
        customerPhoneNumber.setPasswordResetAllowed(false);

        customerPhoneNumberRepository.save(customerPhoneNumber);
        customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);

        if(customer == null){
            throw new UsernameNotFoundException(email);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        Role role = roleRepository.findById(customer.getRoleId()).get();

        authorities.add(new SimpleGrantedAuthority(role.getName().name()));

        return new User(customer.getEmail(), customer.getPassword(), authorities);
    }
}
