package com.flexbank.ws.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at_date")
    private LocalDate createdAtDate;

    @Column(name = "created_at_time")
    private LocalTime createdAtTime;

    @Column(name = "customer_id")
    private Integer customerId;

}
