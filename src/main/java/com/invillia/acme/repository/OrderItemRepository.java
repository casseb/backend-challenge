package com.invillia.acme.repository;

import org.springframework.data.repository.CrudRepository;

import com.invillia.acme.ds.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer>{

}
