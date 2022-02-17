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
@Table(name = "loan_notifications")
public class LoanNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "due_to")
    private LocalDate dueTo;

    @Column(name = "interest_rate")
    private int interestRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LoanRequestType type;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "is_paid")
    private Boolean isPaid;

}
