package com.invillia.acme.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.invillia.acme.common.Constants.*;
import com.invillia.acme.ds.OrderStore;
import com.invillia.acme.service.OrderService;

@RestController
@RequestMapping(REST_ORDER)
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public OrderStore post(@RequestBody OrderStore order) {
		return orderService.insert(order);
	}

	@GetMapping(REST_ORDER_GET)
	public OrderStore getById(@PathVariable Integer id) {
		return orderService.retrieveById(id);
	}
}
