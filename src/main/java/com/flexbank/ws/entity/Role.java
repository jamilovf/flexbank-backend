package com.flexbank.ws.entity;

import lombok.*;

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
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleType name;
}
