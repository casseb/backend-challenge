package com.invillia.acme.repository;

import org.springframework.data.repository.CrudRepository;

import com.invillia.acme.ds.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer>{

}
