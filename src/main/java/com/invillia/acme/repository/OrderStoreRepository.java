package com.invillia.acme.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.invillia.acme.ds.OrderStatus;
import com.invillia.acme.ds.OrderStore;

public interface OrderStoreRepository extends CrudRepository<OrderStore, Integer>{

	public List<OrderStore> findByIdOrConfirmationDateOrStatusOrAddressStreetAddressOrAddressCityOrAddressStateOrAddressZipCode(
			Integer id, LocalDate confirmationDate, OrderStatus orderStatus, String streetAddress, String city,
			String state, String zipCode);

}
