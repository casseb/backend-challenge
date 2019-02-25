package com.invillia.acme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.invillia.acme.ds.Payment;
import com.invillia.acme.ds.PaymentStatus;

public interface PaymentRepository extends CrudRepository<Payment, Integer>{

	public List<Payment> findByOrderIdAndStatus(Integer orderId, PaymentStatus paid);

	public Payment findByOrderId(Integer orderId);

}
