package com.invillia.acme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.invillia.acme.ds.Store;

public interface StoreRepository extends CrudRepository<Store, Integer>{

	public List<Store> findByName(String name);

	public List<Store> findByAddressStreetAddress(String streetAddress);

	public List<Store> findByAddressCity(String city);

	public List<Store> findByAddressState(String state);

	public List<Store> findByAddressZipCode(String zipCode);

}
