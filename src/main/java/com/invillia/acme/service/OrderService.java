package com.invillia.acme.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.ds.OrderNull;
import com.invillia.acme.ds.OrderStatus;
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
		orderStore.setStatus(OrderStatus.PROCESSING);
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
