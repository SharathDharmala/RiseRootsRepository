package com.riseroots;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Customer extends PanacheEntity {
	public String customerName;
	public String category;
    public String occupation;
    public String mobileNumber;
    public String socialSecurityNumber;
    public String taxNumber;
}
