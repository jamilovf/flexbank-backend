package com.flexbank.ws.converter;

import com.flexbank.ws.dto.LoanNotificationDto;
import com.flexbank.ws.entity.LoanNotification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class LoanNotificationConverter {

    @Autowired
    private ModelMapper modelMapper;


    public LoanNotificationDto entityToDto(LoanNotification loanNotification){
        LoanNotificationDto loanNotificationDto = modelMapper.map(loanNotification, LoanNotificationDto.class);
        return loanNotificationDto;
    }

    public LoanNotification entityToDto(LoanNotificationDto loanNotificationDto){
        LoanNotification loanNotification = modelMapper.map(loanNotificationDto, LoanNotification.class);
        return loanNotification;
    }
}
