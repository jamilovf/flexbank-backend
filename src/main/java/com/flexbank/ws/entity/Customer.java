package com.flexbank.ws.entity;

import lombok.*;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "role_id")
    private Integer roleId;

}
