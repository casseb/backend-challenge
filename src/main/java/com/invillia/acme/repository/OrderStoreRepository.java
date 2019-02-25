package com.invillia.acme.repository;

import org.springframework.data.repository.CrudRepository;

import com.invillia.acme.ds.OrderStore;

public interface OrderStoreRepository extends CrudRepository<OrderStore, Integer>{

}
