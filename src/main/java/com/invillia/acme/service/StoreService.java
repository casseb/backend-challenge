package com.invillia.acme.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.ds.Store;
import com.invillia.acme.ds.StoreNull;
import com.invillia.acme.repository.StoreRepository;

@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepository;
	
	public Store save(Store store) {
		return storeRepository.save(store);
	}
	
	public Store retrieveById(Integer storeId) {
		return storeRepository.findById(storeId).orElse(new StoreNull());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<Store> retrieve(Integer id, String name, String streetAddress, String city, String state, String zipCode){
		Set<Store> stores = new HashSet<>();
		
		if(id == null &&
				   name == null && 
				   streetAddress == null && 
				   city == null &&
				   state == null &&
				   zipCode == null) 
		{
					return new HashSet<Store>((Collection)storeRepository.findAll());
		}
		
		if(id != null) {
			stores.add(retrieveById(id));
			return stores;
		}
		
		if(name != null) {
			stores.addAll(storeRepository.findByName(name));
		}
		
		if(streetAddress != null) {
			stores.addAll(storeRepository.findByAddressStreetAddress(streetAddress));
		}
		
		if(city != null) {
			stores.addAll(storeRepository.findByAddressCity(city));
		}
		
		if(state != null) {
			stores.addAll(storeRepository.findByAddressState(state));
		}
		
		if(zipCode != null) {
			stores.addAll(storeRepository.findByAddressZipCode(zipCode));
		}
		
		return stores;
	}

	public void deleteAll() {
		storeRepository.deleteAll();
	}
}
