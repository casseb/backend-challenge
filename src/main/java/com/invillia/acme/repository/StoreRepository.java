package com.invillia.acme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.invillia.acme.ds.Store;

public interface StoreRepository extends CrudRepository<Store, Integer>{

	public List<Store> findByIdOrNameOrAddressStreetAddressOrAddressCityOrAddressStateOrAddressZipCode(Integer id, String name,
			String streetAddress, String city, String state, String zipCode);

}
