package com.flexbank.ws.converter;

import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class CustomerPhoneNumberConverter {

    private ModelMapper modelMapper;

    @Autowired
    public CustomerPhoneNumberConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerPhoneNumberDto entityToDto(CustomerPhoneNumber customerPhoneNumber){
        CustomerPhoneNumberDto customerPhoneNumberDto =
                modelMapper.map(customerPhoneNumber, CustomerPhoneNumberDto.class);
        return customerPhoneNumberDto;
    }

    public CustomerPhoneNumber dtoToEntity(CustomerPhoneNumberDto customerPhoneNumberDto){
        CustomerPhoneNumber customerPhoneNumber =
                modelMapper.map(customerPhoneNumberDto, CustomerPhoneNumber.class);
        return customerPhoneNumber;
    }
}
