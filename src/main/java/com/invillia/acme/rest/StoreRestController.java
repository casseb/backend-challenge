package com.invillia.acme.rest;

import static com.invillia.acme.common.Constants.REST_STORE;
import static com.invillia.acme.common.Constants.REST_STORE_GET;
import static com.invillia.acme.common.Constants.REST_STORE_GET_CITY;
import static com.invillia.acme.common.Constants.REST_STORE_GET_ID;
import static com.invillia.acme.common.Constants.REST_STORE_GET_NAME;
import static com.invillia.acme.common.Constants.REST_STORE_GET_STATE;
import static com.invillia.acme.common.Constants.REST_STORE_GET_STREET_ADDRESS;
import static com.invillia.acme.common.Constants.REST_STORE_GET_ZIPCODE;
import static com.invillia.acme.common.Constants.REST_STORE_PUT;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.ds.Store;
import com.invillia.acme.service.StoreService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(REST_STORE)
@Slf4j
public class StoreRestController {

	@Autowired
	private StoreService storeService;

	@PostMapping
	public Store post(@RequestBody Store store) {
		log.info("Post a new store: {}",store);
		return storeService.save(store);
	}

	@GetMapping(REST_STORE_GET)
	public Store getById(@PathVariable Integer id) {
		log.info("Get a store with id: {}",id);
		return storeService.retrieveById(id);
	}

	@GetMapping
	public Set<Store> getByQueryParam(@RequestParam(value=REST_STORE_GET_ID, required=false) Integer id,
									   @RequestParam(value=REST_STORE_GET_NAME, required=false) String name,
									   @RequestParam(value=REST_STORE_GET_STREET_ADDRESS, required=false) String streetAddress,
									   @RequestParam(value=REST_STORE_GET_CITY, required=false) String city,
									   @RequestParam(value=REST_STORE_GET_STATE, required=false) String state,
									   @RequestParam(value=REST_STORE_GET_ZIPCODE, required=false) String zipCode){
		return storeService.retrieve(id, name, streetAddress, city, state, zipCode);
	}

	@PutMapping(REST_STORE_PUT)
	public Store put(@RequestBody Store store, @PathVariable Integer id) {
		log.info("Edit the store with id: {}",id);
		store.setId(id);;
		return storeService.save(store);
	}
}
