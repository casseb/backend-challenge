package com.invillia.acme.ds;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Address {
	private String streetAddress;

	private String city;

	private String state;

	private String zipCode;
}
