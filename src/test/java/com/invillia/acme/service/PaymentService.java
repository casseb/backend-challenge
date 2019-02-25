package com.invillia.acme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.ds.Payment;
import com.invillia.acme.ds.PaymentStatus;
import com.invillia.acme.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderService orderService;
	
	public Payment payAnOrder(Payment payment) {
		payment.setStatus(PaymentStatus.PENDING);
		return paymentRepository.save(payment);
	}
	
	public boolean isPaidOrder(Integer orderId)
	{
		Integer result = paymentRepository.findByOrderIdAndStatus(orderId, PaymentStatus.PAID).size();
		return result == 1;
	}

	public void refundPayment(Integer orderId) {
		if(orderService.orderAvaliableToRefund(orderId)) {
			List<Payment> payments = paymentRepository.findByOrderIdAndStatus(orderId, PaymentStatus.PAID);
			for(Payment payment:payments) {
				payment.setStatus(PaymentStatus.REFUNDED);
			}
			paymentRepository.saveAll(payments);
		}
	}
}
