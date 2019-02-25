package com.invillia.acme.ds;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "orderStore")
@Data
public class OrderStore{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private LocalDate confirmationDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "orderStore")
	private Set<OrderItem> orderItems = new HashSet<>();
}
