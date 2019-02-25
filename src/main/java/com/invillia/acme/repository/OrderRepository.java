package com.invillia.acme.repository;

import org.springframework.data.repository.CrudRepository;

import com.invillia.acme.ds.OrderStore;

public interface OrderRepository extends CrudRepository<OrderStore, Integer>{

}
