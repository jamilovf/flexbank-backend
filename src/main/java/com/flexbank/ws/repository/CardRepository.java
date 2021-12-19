package com.flexbank.ws.repository;

import com.flexbank.ws.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {

    List<Card> findAllByCustomerId(Integer customerId);
    Card findByCardNumber(String cardNumber);
}
