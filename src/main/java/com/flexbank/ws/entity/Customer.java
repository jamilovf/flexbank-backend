package com.flexbank.ws.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "zip")
    private String zip;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private List<Card> cardList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private List<CardOrder> cardOrderList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private List<LoanNotification> loanNotificationList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private List<LoanRequest> loanRequestList;

    @OneToMany(mappedBy = "customerId")
    private List<Transaction> transactionList;

}
