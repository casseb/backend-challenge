package com.invillia.acme.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.invillia.acme.common.Constants.*;
import com.invillia.acme.ds.Payment;
import com.invillia.acme.ds.PaymentResponse;
import com.invillia.acme.service.PaymentService;

@RestController
@RequestMapping(REST_PAYMENT)
public class PaymentRestController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	public Payment post(@RequestBody Payment payment) {
		return paymentService.payOrder(payment);
	}
	
	@PostMapping(REST_PAYMENT_REFUND)
	public PaymentResponse refund(@PathVariable Integer orderId) {
		return paymentService.refundPayment(orderId);
	}
	
	@PostMapping(REST_PAYMENT_CONFIRM)
	public Payment confirm(@PathVariable Integer id) {
		return paymentService.confirmPayment(id);
	}
	
	@GetMapping
	public Payment get(@RequestParam(value=REST_PAYMENT_GET_ID, required=false) Integer id,
					   @RequestParam(value=REST_PAYMENT_GET_ORDER, required=false) Integer order)
	{
		return paymentService.retrieve(id, order);
	}
}
