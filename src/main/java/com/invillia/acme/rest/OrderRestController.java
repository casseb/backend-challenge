package com.invillia.acme.rest;

import static com.invillia.acme.common.Constants.REST_ORDER;
import static com.invillia.acme.common.Constants.REST_ORDER_GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.ds.OrderStore;
import com.invillia.acme.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(REST_ORDER)
@Slf4j
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public OrderStore post(@RequestBody OrderStore order) {
		log.info("Post a new order: {}",order);
		return orderService.insert(order);
	}

	@GetMapping(REST_ORDER_GET)
	public OrderStore getById(@PathVariable Integer id) {
		log.info("Get an order with id: {}",id);
		return orderService.retrieveById(id);
	}
}
