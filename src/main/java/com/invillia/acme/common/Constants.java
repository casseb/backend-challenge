package com.invillia.acme.common;

import java.util.HashSet;
import java.util.Set;

import com.invillia.acme.ds.OrderItem;
import com.invillia.acme.ds.OrderStatus;
import com.invillia.acme.ds.OrderStore;
import com.invillia.acme.ds.Payment;
import com.invillia.acme.ds.PaymentStatus;
import com.invillia.acme.ds.Store;

public class Constants {

	//Order Rests
	public static final String REST_ORDER = "/order";
	public static final String REST_ORDER_GET = "/{id}";
	
	//Payment Rests
	public static final String REST_PAYMENT = "/payment";
	public static final String REST_PAYMENT_REFUND = "/refund/{orderId}";
	public static final String REST_PAYMENT_CONFIRM = "/confirm/{id}";
	public static final String REST_PAYMENT_GET_ID = "id";
	public static final String REST_PAYMENT_GET_ORDER = "order";
	
	//Store Rests
	public static final String REST_STORE = "/store";
	public static final String REST_STORE_GET = "/{id}";
	public static final String REST_STORE_PUT = "/{id}";
	public static final String REST_STORE_GET_ID = "id";
	public static final String REST_STORE_GET_NAME = "name";
	public static final String REST_STORE_GET_STREET_ADDRESS = "streetAddress";
	public static final String REST_STORE_GET_CITY = "city";
	public static final String REST_STORE_GET_STATE = "state";
	public static final String REST_STORE_GET_ZIPCODE = "zipCode";
	
	//Payment Responses
	public static final String PAYMENT_REFUNDED = "Refunded";
	public static final String PAYMENT_NOT_REFUNDED = "Not Refunded";
	
	//Defaults
	public static final OrderStatus INITIAL_ORDER_STATUS = OrderStatus.PROCESSING;
	public static final PaymentStatus INITIAL_PAYMENT_STATUS = PaymentStatus.PENDING;
	
	//Tests
	private static final String STORE_NAME_TEST = "StoreTest";
	private static final String CREDIT_CARD = "12345";
	
	public static Store getEmptyStoreTest(){
		return new Store();
	}
	
	public static Store getStoreTest() {
		return Store.builder()
					.name(STORE_NAME_TEST)
					.build();
	}
	
	public static OrderStore getEmptyOrder() {
		return new OrderStore();
	}
	
	public static OrderStore getOrderWithItems() {
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
	
	public static Payment getPayment(Integer order) {
		return Payment.builder()
						.orderId(order)
						.creditCardNumber(CREDIT_CARD)
						.build();
	}
}
