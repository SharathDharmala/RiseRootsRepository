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

    @Column(name = "customer_name") // Fix column name
    private String customerName;

    @Column(name = "mobile_number") // Fix column name
    private String mobileNumber;

    @Column(name = "social_security_number") // Fix column name
    private String socialSecurityNumber;

    @Column(name = "tax_number") // Fix column name
    private String taxNumber;

    @Column(name = "category") // Fix column name
    private String category;

    @Column(name = "occupation") // Fix column name
    private String occupation;
}
