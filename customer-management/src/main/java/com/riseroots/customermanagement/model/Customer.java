package com.riseroots.customermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("category")
    private String category;

    @JsonProperty("occupation")
    private String occupation;

    @JsonProperty("mobileNumber")
    private String mobileNumber;
}
