package com.invillia.acme.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.invillia.acme.common.TestCase;
import com.invillia.acme.ds.OrderStatus;
import com.invillia.acme.ds.OrderStore;
import com.invillia.acme.ds.Store;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests extends TestCase {

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
		assertEquals(OrderStatus.PROCESSING, order.getStatus());
	}

	@Test
	public void testInsertOrderWithItems() {
		// given
		OrderStore order = getOrderWithItems();

		// when
		order = orderService.insert(order);

		// then
		assertEquals(OrderStatus.PROCESSING, order.getStatus());
		assertEquals(5,order.getOrderItems().size());
	}
}
