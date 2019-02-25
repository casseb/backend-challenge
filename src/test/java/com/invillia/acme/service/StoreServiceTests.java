package com.invillia.acme.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.invillia.acme.common.TestCase;
import com.invillia.acme.ds.Store;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreServiceTests extends TestCase{
	
	@Autowired
	private StoreService storeService;
	
	@Before
	public void before() {
		storeService.deleteAll();
	}
	
	@Test
	public void testUpdateInvalidStore() {
		//given
		Store store = getEmptyStoreTest();
		
		//when
		store = storeService.update(store);
		
		//then
		assertNull(store);
	}
	
	@Test
	public void testUpdateValidStore() {
		//given
		String newName = "Nome Alterado";
		Store store = storeService.insert(getStoreTest());
		Integer storeId = store.getId();
		store.setName(newName);
		
		//when
		store = storeService.update(store);
		
		//then
		assertEquals(newName, store.getName());
		assertEquals(storeId, store.getId());
	}
	
	@Test
	public void testRetrieveWithOneArg()
	{
		//given
		Store store = storeService.insert(getStoreTest());
		
		//when
		List<Store> stores = storeService.retrieve(null, store.getName(), null, null, null, null);
		
		//then
		assertEquals(1,stores.size());
	}
	
	@Test
	public void testRetrieveWithTwoArgs()
	{
		//given
		Store store = storeService.insert(getStoreTest());
		Integer storeId = store.getId();
		store = storeService.insert(getStoreTest());
		String storeName = store.getName();
		
		//when
		List<Store> stores = storeService.retrieve(storeId, storeName, null, null, null, null);
		
		//then
		assertEquals(2,stores.size());
	}
}
