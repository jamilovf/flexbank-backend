package com.flexbank.ws.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 *
 * @author Fuad Jamilov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "customer_phone_numbers")
public class CustomerPhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_registered")
    private boolean isRegistered;

    @Column(name = "message_code")
    private String messageCode;

    @Column(name = "is_message_code_allowed")
    private boolean isMessageCodeAllowed;

    @Column(name = "is_signup_allowed")
    private boolean isSignupAllowed;

}
