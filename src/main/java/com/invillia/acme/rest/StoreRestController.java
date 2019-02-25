package com.invillia.acme.rest;

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

import static com.invillia.acme.common.Constants.*;
import com.invillia.acme.ds.Store;
import com.invillia.acme.service.StoreService;

@RestController
@RequestMapping(REST_STORE)
public class StoreRestController {

	@Autowired
	private StoreService storeService;

	@PostMapping
	public Store post(@RequestBody Store store) {
		return storeService.save(store);
	}

	@GetMapping(REST_STORE_GET)
	public Store getById(@PathVariable Integer id) {
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
		store.setId(id);;
		return storeService.save(store);
	}
}
