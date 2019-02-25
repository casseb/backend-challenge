package com.invillia.acme.rest;

import static com.invillia.acme.common.Constants.INITIAL_PAYMENT_STATUS;
import static com.invillia.acme.common.Constants.PAYMENT_REFUNDED;
import static com.invillia.acme.common.Constants.REST_ORDER;
import static com.invillia.acme.common.Constants.REST_ORDER_GET;
import static com.invillia.acme.common.Constants.REST_PAYMENT;
import static com.invillia.acme.common.Constants.REST_PAYMENT_CONFIRM;
import static com.invillia.acme.common.Constants.REST_PAYMENT_REFUND;
import static com.invillia.acme.common.Constants.REST_STORE;
import static com.invillia.acme.common.Constants.REST_STORE_PUT;
import static com.invillia.acme.common.Constants.getOrderWithItems;
import static com.invillia.acme.common.Constants.getPayment;
import static com.invillia.acme.common.Constants.getStoreTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.invillia.acme.ds.Store;
import com.invillia.acme.service.StoreService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests{
	
	@LocalServerPort
	private int port;

	@Autowired
	private StoreService storeService;
	
	@Before
	public void before() {
		RestAssured.port = port;
		storeService.deleteAll();
	}
	
	@Test
	public void createStore() {
		given().
			body(getStoreTest()).
			contentType(ContentType.JSON).
		when().
			post(REST_STORE).
		then().
			assertThat().
				statusCode(200).
				body("name", equalTo(getStoreTest().getName()));
	}
	
	@Test
	public void updateStoreInformation() {
		Integer originalStoreId = getPersistedStore();
		String altName = "AlterName";
		Store storeEdited = getStoreTest();
		storeEdited.setName(altName);
		
		given().
			body(storeEdited).
			contentType(ContentType.JSON).
			pathParam("id",originalStoreId).
		when().
			put(REST_STORE+REST_STORE_PUT).
		then().
			assertThat().
				statusCode(200).
				body("name", equalTo(altName));
	}
	
	@Test
	public void retrieveStoreByParameters() {		
		Integer originalStoreId = getPersistedStore();
		
		given().
			queryParam("id", originalStoreId).
			queryParam("name", getStoreTest().getName()).
		when().
			get(REST_STORE).
		then().
			assertThat().
				statusCode(200).
				body("name", equalTo(Arrays.asList(getStoreTest().getName())));
	}
	
	@Test
	public void createOrderWithItems() {
		given().
			body(getOrderWithItems()).
			contentType(ContentType.JSON).
		when().
			post(REST_ORDER).
		then().
			assertThat().
				statusCode(200).
				body("orderItems.size()",equalTo(5));
	}
	
	@Test
	public void createPaymentForAnOrder() {
		Integer orderId = getPersistedOrder();
		
		given().
			body(getPayment(orderId)).
			contentType(ContentType.JSON).
		when().
			post(REST_PAYMENT).
		then().
			assertThat().
				statusCode(200).
				body("status",equalTo(INITIAL_PAYMENT_STATUS.name())).and().body("orderId", equalTo(orderId));
	}
	
	@Test
	public void retrieveOrderByParameter() {
		Integer orderId = getPersistedOrder();
		
		given().
			pathParam("id", orderId).
		when().
			get(REST_ORDER+REST_ORDER_GET).
		then().
			assertThat().
				statusCode(200).
				body("orderItems.size()",equalTo(5));
	}
	
	@Test
	public void refundOrder() {
		Integer orderPaid = getOrderWithPaymentPaid();
		
		given().
			pathParam("orderId", orderPaid).
			contentType(ContentType.JSON).
			
		when().
			post(REST_PAYMENT+REST_PAYMENT_REFUND).
		then().
			assertThat().
				statusCode(200).
				body("result",is(PAYMENT_REFUNDED));
	}
	
	private Integer getOrderWithPaymentPaid() {
		Integer orderId = getPersistedOrder();
		Integer paymentId = getPersistedPayment(orderId);
		
		given().
			pathParam("id",paymentId).
		when().
			post(REST_PAYMENT+REST_PAYMENT_CONFIRM).
		then().
			assertThat().
				statusCode(200).
				body("status",equalTo("PAID"));
		
		return orderId;
	}
	
	private Integer getPersistedPayment(Integer orderId) {
		return
		given().
			body(getPayment(orderId)).
			contentType(ContentType.JSON).
		when().
			post(REST_PAYMENT).
		then().
			statusCode(200).
		extract().
			path("id");
	}
	
	private Integer getPersistedStore() {
		return
		given().
			body(getStoreTest()).
			contentType(ContentType.JSON).
		when().
			post(REST_STORE).
		then().
			statusCode(200).
		extract().
			path("id");
	}
	
	private Integer getPersistedOrder() {
		return
		given().
			body(getOrderWithItems()).
			contentType(ContentType.JSON).
		when().
			post(REST_ORDER).
		then().
			statusCode(200).
		extract().
			path("id");
	}
}
