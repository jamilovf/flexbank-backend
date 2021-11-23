package com.flexbank.ws.service;

import com.flexbank.ws.dto.CardDto;

import java.util.List;

public interface CardService {

    List<CardDto> findAllByCustomerId(Integer customerId);
}
