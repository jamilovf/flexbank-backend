package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.CardDto;

import java.util.List;

public interface CardService {

    List<CardDto> findAllByCustomerId(Integer customerId);

    void blockCard(Integer id) throws Exception;
}
