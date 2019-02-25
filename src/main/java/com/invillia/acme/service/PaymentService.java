package com.invillia.acme.service;

import static com.invillia.acme.common.Constants.INITIAL_PAYMENT_STATUS;
import static com.invillia.acme.common.Constants.PAYMENT_NOT_REFUNDED;
import static com.invillia.acme.common.Constants.PAYMENT_REFUNDED;

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
import com.invillia.acme.ds.PaymentResponse;
import com.invillia.acme.ds.PaymentStatus;
import com.invillia.acme.repository.OrderStoreRepository;
import com.invillia.acme.repository.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderStoreRepository orderRepository;
	
	public Payment payOrder(Payment payment) {
		payment.setStatus(INITIAL_PAYMENT_STATUS);
		payment.setPaymentDate(LocalDate.now());
		return paymentRepository.save(payment);
	}

	public PaymentResponse refundPayment(Integer orderId) {
		if(orderAvaliableToRefund(orderId)) {
			log.debug("Order {} can be refunded",orderId);
			List<Payment> payments = paymentRepository.findByOrderIdAndStatus(orderId, PaymentStatus.PAID);
			for(Payment payment:payments) {
				payment.setStatus(PaymentStatus.REFUNDED);
				log.debug("Payment {} will be refunded",payment.getId());
			}
			paymentRepository.saveAll(payments);
			
			OrderStore orderToRefund = orderRepository.findById(orderId).get();
			orderToRefund.setStatus(OrderStatus.CANCELLED);
			orderRepository.save(orderToRefund);
			
			return getPaymentResponse(PAYMENT_REFUNDED);
		}
		log.debug("Order {} can't be refunded",orderId);
		return getPaymentResponse(PAYMENT_NOT_REFUNDED);
	}

	public Payment confirmPayment(Integer paymentId) {
		Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
		if(!optionalPayment.isPresent()) {
			log.debug("Payment {} wasn't found",paymentId);
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
	
	private boolean orderConfirmedUntilTenDays(Integer orderId) {
		OrderStore order = orderRepository.findById(orderId).orElse(new OrderNull());
		if(order.getId() == 0) {
			log.debug("Order {} wasn't found",orderId);
			return false;
		}
		
		LocalDate confirmationDate = order.getConfirmationDate();
		LocalDate today = LocalDate.now();
		LocalDate dueDate = confirmationDate.plusDays(11);
		
		if(dueDate.isAfter(today)) {
			return true;
		}
		log.debug("Order {} has expired the deadline for refund with dueDate: ",orderId,dueDate);
		return false;
	}
	
	private boolean orderAvaliableToRefund(Integer orderId) {
		return (orderConfirmedUntilTenDays(orderId) && isPaidOrder(orderId));
	}
	
	private boolean isPaidOrder(Integer orderId)
	{
		Integer result = paymentRepository.findByOrderIdAndStatus(orderId, PaymentStatus.PAID).size();
		return result == 1;
	}
	
	private PaymentResponse getPaymentResponse(String message) {
		return PaymentResponse.builder().result(message).build();
	}
}
