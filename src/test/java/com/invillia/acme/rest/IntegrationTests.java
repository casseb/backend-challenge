package com.invillia.acme.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.time.Clock;
import java.time.ZoneId;
import java.util.Arrays;

import org.hamcrest.Matcher;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import com.invillia.acme.common.TestCase;
import com.invillia.acme.ds.Store;
import com.invillia.acme.service.StoreService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests extends TestCase{
	
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
			post("/store").
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
			put("/store/{id}").
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
			get("/store").
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
			post("/order").
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
			post("/payment").
		then().
			assertThat().
				statusCode(200).
				body("status",equalTo("PENDING")).and().body("orderId", equalTo(orderId));
	}
	
	@Test
	public void retrieveOrderByParameter() {
		Integer orderId = getPersistedOrder();
		
		given().
			pathParam("id", orderId).
		when().
			get("/order/{id}").
		then().
			assertThat().
				statusCode(200).
				body("orderItems.size()",equalTo(5));
	}
	
	@Test
	public void refundOrder() {
		Integer orderPaid = getOrderWithPaymentPaid();
		
		given().
			pathParam("id", orderPaid).
			contentType(ContentType.JSON).
			
		when().
			post("/payment/refund/{id}").
		then().
			assertThat().
				statusCode(200).
				body("result",is("Refunded"));
	}
	
	private Integer getOrderWithPaymentPaid() {
		Integer orderId = getPersistedOrder();
		Integer paymentId = getPersistedPayment(orderId);
		
		given().
			pathParam("id",paymentId).
		when().
			post("/payment/confirm/{id}").
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
			post("/payment").
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
			post("/store").
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
			post("/order").
		then().
			statusCode(200).
		extract().
			path("id");
	}
}
