package com.invillia.acme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.ds.Store;
import com.invillia.acme.repository.StoreRepository;

@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepository;
	
	public Store insert(Store store) {
		return storeRepository.save(store);
	}
	
	public Store update(Store store) {
		Integer storeId = store.getId();
		if(storeId != null && storeRepository.existsById(storeId))
		{
			return storeRepository.save(store);
		}
		
		return null;
	}
	
	public List<Store> retrieve(Integer id, String name, String streetAddress, String city, String state, String zipCode){
		return 
			storeRepository.
			findByIdOrNameOrAddressStreetAddressOrAddressCityOrAddressStateOrAddressZipCode
			(id,name,streetAddress,city,state,zipCode);
	}

	public void deleteAll() {
		storeRepository.deleteAll();
	}
}
