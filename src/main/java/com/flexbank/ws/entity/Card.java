package com.flexbank.ws.entity;

import lombok.*;

import java.time.LocalDate;
import javax.persistence.*;

/**
 *
 * @author Fuad Jamilov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

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
