package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.CardDto;
import com.flexbank.ws.dto.request.CardOrderRequest;
import com.flexbank.ws.exception.BadRequestException;

import java.util.List;

public interface CardService {

    List<CardDto> findAllByCustomerId();

    CardDto blockCard(Integer id) throws Exception;

    void createCard(CardOrderRequest cardOrderRequest);

    CardDto unblockCard(Integer id) throws BadRequestException;
}
