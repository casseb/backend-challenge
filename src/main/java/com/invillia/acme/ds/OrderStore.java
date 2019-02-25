package com.invillia.acme.ds;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Entity
@Table(name = "orderStore")
@Data
@Builder
public class OrderStore{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private LocalDate confirmationDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Embedded
	private Address address;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderItem> orderItems;

	@Tolerate
	public OrderStore() {}
}
