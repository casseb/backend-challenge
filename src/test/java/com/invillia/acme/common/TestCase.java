package com.invillia.acme.common;

import com.invillia.acme.ds.Store;
import static org.mockito.Mockito.*;

public class TestCase {
	
	public Store getEmptyStoreTest(){
		return Store.builder().build();
	}
	
}
