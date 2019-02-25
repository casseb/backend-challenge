package com.invillia.acme.service;

import static com.invillia.acme.common.Constants.getEmptyOrder;
import static com.invillia.acme.common.Constants.getOrderWithItems;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.invillia.acme.common.Constants.*;
import com.invillia.acme.ds.OrderStore;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests{

	@Autowired
	private OrderService orderService;

	@Before
	public void before() {
		orderService.deleteAll();
	}

	@Test
	public void testInsertEmptyOrder() {
		// given
		OrderStore order = getEmptyOrder();

		// when
		order = orderService.insert(order);

		// then
		assertEquals(INITIAL_ORDER_STATUS, order.getStatus());
	}

	@Test
	public void testInsertOrderWithItems() {
		// given
		OrderStore order = getOrderWithItems();

		// when
		order = orderService.insert(order);

		// then
		assertEquals(INITIAL_ORDER_STATUS, order.getStatus());
		assertEquals(5,order.getOrderItems().size());
	}
}
