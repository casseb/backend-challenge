package com.invillia.acme.ds;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Embeddable
public class Address {
	private String streetAddress;

	private String city;

	private String state;

	private String zipCode;
}
