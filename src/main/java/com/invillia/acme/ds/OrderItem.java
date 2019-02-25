package com.invillia.acme.ds;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "orderItem")
@Data
public class OrderItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private Integer unit;

	private Float quantity;
	
	@ManyToOne
    private OrderStore orderStore;
}
