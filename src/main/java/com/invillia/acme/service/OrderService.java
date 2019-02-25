package com.invillia.acme.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	@Autowired
	private PaymentService paymentService;
	
	public OrderStore insert(OrderStore orderStore) {
		orderStore.setStatus(OrderStatus.PROCESSING);
		orderStore.setConfirmationDate(LocalDate.now());
		return orderRepository.save(orderStore);
	}
	
	public List<OrderStore> retrieve(Integer id, LocalDate confirmationDate, OrderStatus orderStatus, String streetAddress, String city, String state, String zipCode){
		return orderRepository.
				findByIdOrConfirmationDateOrStatusOrAddressStreetAddressOrAddressCityOrAddressStateOrAddressZipCode(
						id, confirmationDate, orderStatus, streetAddress, city, state, zipCode);
	}
	
	public boolean orderAvaliableToRefund(Integer orderId) {
		return (orderConfirmedUntilTenDays(orderId) && paymentService.isPaidOrder(orderId));
	}
	
	public boolean orderConfirmedUntilTenDays(Integer orderId) {
		Optional<OrderStore> order = orderRepository.findById(orderId);
		if(!order.isPresent()) {
			return false;
		}
		
		LocalDate confirmationDate = order.get().getConfirmationDate();
		LocalDate today = LocalDate.now();
		LocalDate dueDate = confirmationDate.plusDays(11);
		
		if(dueDate.isBefore(today)) {
			return true;
		}
		return false;
	}

	public void deleteAll() {
		orderRepository.deleteAll();
		orderItemRepository.deleteAll();
	}
}
