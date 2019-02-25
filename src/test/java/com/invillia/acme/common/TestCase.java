package com.invillia.acme.common;

import com.invillia.acme.ds.Store;
import static org.mockito.Mockito.*;

public class TestCase {
	
	private static String STORE_NAME_TEST = "Store Test";
	
	public Store getEmptyStoreTest(){
		return Store.builder().build();
	}
	
	public Store getStoreTest() {
		return Store.builder()
					.name(STORE_NAME_TEST)
					.build();
	}
	
}
