package com.invillia.acme.rest;

import static com.invillia.acme.common.Constants.REST_PAYMENT;
import static com.invillia.acme.common.Constants.REST_PAYMENT_CONFIRM;
import static com.invillia.acme.common.Constants.REST_PAYMENT_GET_ID;
import static com.invillia.acme.common.Constants.REST_PAYMENT_GET_ORDER;
import static com.invillia.acme.common.Constants.REST_PAYMENT_REFUND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.ds.Payment;
import com.invillia.acme.ds.PaymentResponse;
import com.invillia.acme.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(REST_PAYMENT)
@Slf4j
public class PaymentRestController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	public Payment post(@RequestBody Payment payment) {
		log.info("Post a new Payment: {}",payment);
		return paymentService.payOrder(payment);
	}
	
	@PostMapping(REST_PAYMENT_REFUND)
	public PaymentResponse refund(@PathVariable Integer orderId) {
		log.info("Refund the order :{}",orderId);
		return paymentService.refundPayment(orderId);
	}
	
	@PostMapping(REST_PAYMENT_CONFIRM)
	public Payment confirm(@PathVariable Integer id) {
		log.info("Confirm the payment: {}",id);
		return paymentService.confirmPayment(id);
	}
	
	@GetMapping
	public Payment get(@RequestParam(value=REST_PAYMENT_GET_ID, required=false) Integer id,
					   @RequestParam(value=REST_PAYMENT_GET_ORDER, required=false) Integer order)
	{
		log.info("Query payments with id: {} and order: {}",id,order);
		return paymentService.retrieve(id, order);
	}
}
