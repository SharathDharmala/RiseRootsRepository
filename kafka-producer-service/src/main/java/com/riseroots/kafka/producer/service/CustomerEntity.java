package com.riseroots.kafka.producer.service;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customername")
    private String customerName;

    @Column(name = "mobilenumber")
    private String mobileNumber;

    @Column(name = "socialsecuritynumber")
    private String socialSecurityNumber;

    @Column(name = "taxnumber")
    private String taxNumber;
}
