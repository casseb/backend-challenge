package com.invillia.acme.service;

import static com.invillia.acme.common.Constants.INITIAL_ORDER_STATUS;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.ds.OrderNull;
import com.invillia.acme.ds.OrderStore;
import com.invillia.acme.repository.OrderItemRepository;
import com.invillia.acme.repository.OrderStoreRepository;

@Service
public class OrderService {

	@Autowired
	private OrderStoreRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;

	public OrderStore insert(OrderStore orderStore) {
		orderStore.setStatus(INITIAL_ORDER_STATUS);
		orderStore.setConfirmationDate(LocalDate.now());
		return orderRepository.save(orderStore);
	}

	public OrderStore retrieveById(Integer orderId){
		return orderRepository.findById(orderId).orElse(new OrderNull());
	}

	public void deleteAll() {
		orderRepository.deleteAll();
		orderItemRepository.deleteAll();
	}
}
