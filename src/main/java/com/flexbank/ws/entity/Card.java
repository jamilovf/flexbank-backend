package com.flexbank.ws.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Fuad Jamilov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @Column(name = "is_expired")
    private Boolean isExpired;
}
