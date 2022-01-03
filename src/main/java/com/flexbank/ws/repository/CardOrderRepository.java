package com.flexbank.ws.repository;

import com.flexbank.ws.entity.CardOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardOrderRepository extends JpaRepository<CardOrder, Integer> {
}
