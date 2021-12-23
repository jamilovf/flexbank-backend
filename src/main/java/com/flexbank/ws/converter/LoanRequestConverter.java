package com.flexbank.ws.converter;

import com.flexbank.ws.dto.LoanRequestDto;
import com.flexbank.ws.entity.LoanRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class LoanRequestConverter {

    private ModelMapper modelMapper;

    @Autowired
    public LoanRequestConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LoanRequestDto entityToDto(LoanRequest loanRequest){
        LoanRequestDto loanRequestDto = modelMapper.map(loanRequest, LoanRequestDto.class);
        return loanRequestDto;
    }

    public LoanRequest dtoToEntity(LoanRequestDto loanRequestDto){
        LoanRequest loanRequest = modelMapper.map(loanRequestDto, LoanRequest.class);
        return loanRequest;
    }
}
