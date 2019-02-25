package com.invillia.acme.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.ds.OrderNull;
import com.invillia.acme.ds.OrderStatus;
import com.invillia.acme.ds.OrderStore;
import com.invillia.acme.ds.Payment;
import com.invillia.acme.ds.PaymentNull;
import com.invillia.acme.ds.PaymentStatus;
import com.invillia.acme.repository.OrderStoreRepository;
import com.invillia.acme.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderStoreRepository orderStoreRepository;
	
	public Payment payAnOrder(Payment payment) {
		payment.setStatus(PaymentStatus.PENDING);
		payment.setPaymentDate(LocalDate.now());
		return paymentRepository.save(payment);
	}
	
	public boolean isPaidOrder(Integer orderId)
	{
		Integer result = paymentRepository.findByOrderIdAndStatus(orderId, PaymentStatus.PAID).size();
		return result == 1;
	}

	public boolean refundPayment(Integer orderId) {
		if(orderAvaliableToRefund(orderId)) {
			List<Payment> payments = paymentRepository.findByOrderIdAndStatus(orderId, PaymentStatus.PAID);
			for(Payment payment:payments) {
				payment.setStatus(PaymentStatus.REFUNDED);
			}
			paymentRepository.saveAll(payments);
			OrderStore orderToRefund = orderStoreRepository.findById(orderId).get();
			orderToRefund.setStatus(OrderStatus.CANCELLED);
			orderStoreRepository.save(orderToRefund);
			return true;
		}
		return false;
	}
	
	public boolean orderAvaliableToRefund(Integer orderId) {
		return (orderConfirmedUntilTenDays(orderId) && isPaidOrder(orderId));
	}
	
	public boolean orderConfirmedUntilTenDays(Integer orderId) {
		OrderStore order = orderStoreRepository.findById(orderId).orElse(new OrderNull());
		if(order.getId() == 0) {
			return false;
		}
		
		LocalDate confirmationDate = order.getConfirmationDate();
		LocalDate today = LocalDate.now();
		LocalDate dueDate = confirmationDate.plusDays(11);
		
		if(dueDate.isAfter(today)) {
			return true;
		}
		return false;
	}

	public Payment confirmPayment(Integer paymentId) {
		Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
		if(!optionalPayment.isPresent()) {
			return new PaymentNull();
		}
		
		Payment payment = optionalPayment.get();
		payment.setStatus(PaymentStatus.PAID);
		return paymentRepository.save(payment);
	}

	public Payment retrieve(Integer id, Integer orderId) {
		if(id != null) {
			return paymentRepository.findById(id).orElse(new PaymentNull());
		}
		
		return paymentRepository.findByOrderId(orderId);
	}
}
