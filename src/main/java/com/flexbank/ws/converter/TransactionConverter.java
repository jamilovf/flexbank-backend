package com.flexbank.ws.converter;

import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.entity.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class TransactionConverter {

    private ModelMapper modelMapper;

    @Autowired
    public TransactionConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionDto entityToDto(Transaction transaction){
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        return transactionDto;
    }

    public Transaction dtoToEntity(TransactionDto transactionDto){
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        return transaction;
    }
}
