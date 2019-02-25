package com.invillia.acme.common;

import com.invillia.acme.ds.OrderItem;
import com.invillia.acme.ds.OrderStore;
import com.invillia.acme.ds.Payment;
import com.invillia.acme.ds.Store;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

public class TestCase {
	
	private static final String STORE_NAME_TEST = "StoreTest";
	private static final String CREDIT_CARD = "12345";
	
	public Store getEmptyStoreTest(){
		return new Store();
	}
	
	public Store getStoreTest() {
		return Store.builder()
					.name(STORE_NAME_TEST)
					.build();
	}
	
	public OrderStore getEmptyOrder() {
		return new OrderStore();
	}
	
	public OrderStore getOrderWithItems() {
		Set<OrderItem> items = new HashSet<>();
		for(int i = 1; i <= 5; i++) {
			OrderItem item = OrderItem.builder()
									  .description("Item numero "+i)
									  .quantity((float) 1)
									  .unit((float)2)
									  .build();
			items.add(item);
		}
		return OrderStore.builder()
							.orderItems(items)
							.build();
	}
	
	public Payment getPayment(Integer order) {
		return Payment.builder()
						.orderId(order)
						.creditCardNumber(CREDIT_CARD)
						.build();
	}
}
