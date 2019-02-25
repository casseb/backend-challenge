package com.invillia.acme.service;

import static com.invillia.acme.common.Constants.getStoreTest;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.invillia.acme.ds.Store;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreServiceTests{
	
	@Autowired
	private StoreService storeService;
	
	@Before
	public void before() {
		storeService.deleteAll();
	}
	
	@Test
	public void testUpdateValidStore() {
		//given
		String newName = "Nome Alterado";
		Store store = storeService.save(getStoreTest());
		Integer storeId = store.getId();
		store.setName(newName);
		
		//when
		store = storeService.save(store);
		
		//then
		assertEquals(newName, store.getName());
		assertEquals(storeId, store.getId());
	}
	
	@Test
	public void testRetrieveWithOneArg()
	{
		//given
		Store store = storeService.save(getStoreTest());
		
		//when
		Set<Store> stores = storeService.retrieve(null, store.getName(), null, null, null, null);
		
		//then
		assertEquals(1,stores.size());
	}
}
