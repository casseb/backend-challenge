package com.invillia.acme.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.ds.OrderStore;
import com.invillia.acme.ds.Store;
import com.invillia.acme.service.OrderService;

@RestController
public class OrderRestController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order")
	public OrderStore post(@RequestBody OrderStore order) {
		return orderService.insert(order);
	}
	
	@GetMapping("/order/{id}")
	public OrderStore getById(@PathVariable Integer id) {
		return orderService.retrieveById(id);
	}
}
