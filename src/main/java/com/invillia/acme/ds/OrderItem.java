package com.invillia.acme.ds;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Entity
@Table(name = "orderItem")
@Data
@Builder
public class OrderItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private Float unit;

	private Float quantity;

	@Tolerate
	public OrderItem() {};
}
