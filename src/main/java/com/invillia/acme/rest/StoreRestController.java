package com.invillia.acme.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.ds.Store;
import com.invillia.acme.service.StoreService;

@RestController
public class StoreRestController {

	@Autowired
	private StoreService storeService;
	
	@PostMapping("/store")
	public Store post(@RequestBody Store store) {
		return storeService.save(store);
	}
	
	@GetMapping("/store/{id}")
	public Store getById(@PathVariable Integer id) {
		return storeService.retrieveById(id);
	}
	
	@GetMapping("/store")
	public Set<Store> getByQueryParam(@RequestParam(value="id", required=false) Integer id,
									   @RequestParam(value="name", required=false) String name,
									   @RequestParam(value="streetAddress", required=false) String streetAddress,
									   @RequestParam(value="city", required=false) String city,
									   @RequestParam(value="state", required=false) String state,
									   @RequestParam(value="zipCode", required=false) String zipCode){
		return storeService.retrieve(id, name, streetAddress, city, state, zipCode);
	}
	
	@PutMapping("/store/{id}")
	public Store put(@RequestBody Store store, @PathVariable Integer id) {
		store.setId(id);;
		return storeService.save(store);
	}
}
