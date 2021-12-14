package com.flexbank.ws.converter;

import com.flexbank.ws.dto.CustomerDto;
import com.flexbank.ws.entity.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class CustomerConverter {
    private ModelMapper modelMapper;

    @Autowired
    public CustomerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerDto entityToDto(Customer customer){
        CustomerDto customerDto =
                modelMapper.map(customer, CustomerDto.class);
        return customerDto;
    }

    public Customer dtoToEntity(CustomerDto customerDto){
        Customer customer =
                modelMapper.map(customerDto, Customer.class);
        return customer;
    }
}
