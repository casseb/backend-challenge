package com.invillia.acme.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.ds.Payment;
import com.invillia.acme.ds.PaymentResponse;
import com.invillia.acme.service.PaymentService;

@RestController
public class PaymentRestController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/payment")
	public Payment post(@RequestBody Payment payment) {
		return paymentService.payAnOrder(payment);
	}
	
	@PostMapping("/payment/refund/{id}")
	public PaymentResponse refund(@PathVariable Integer id) {
		Boolean refunded =  paymentService.refundPayment(id);
		if(refunded) {
			return PaymentResponse.builder().result("Refunded").build();
		}
		return PaymentResponse.builder().result("Not Refunded").build();
	}
	
	@PostMapping("/payment/confirm/{id}")
	public Payment confirm(@PathVariable Integer id) {
		return paymentService.confirmPayment(id);
	}
	
	@GetMapping("/payment/")
	public Payment get(@RequestParam(value="id", required=false) Integer id,
					   @RequestParam(value="order", required=false) Integer order) {
		return paymentService.retrieve(id, order);
	}
}
