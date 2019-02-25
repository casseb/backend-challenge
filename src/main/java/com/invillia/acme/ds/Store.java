package com.invillia.acme.ds;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Entity
@Table(name = "store")
@Data
@Builder
public class Store {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String name;

	@Embedded
	private Address address;

	@Tolerate
	public Store(){};
}
