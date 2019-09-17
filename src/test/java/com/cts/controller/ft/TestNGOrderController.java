package com.cts.controller.ft;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cts.model.Order;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestNGOrderController {

	@Test
	public void testAddOrder() throws Exception {

		String url = "http://localhost:9090/orders";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Order order = new Order();
		order.setOrderId("OR-123");
		order.setOrderDate("2019-07-11");
		order.setProdId("PROD-123");
		order.setUserID("USR-123");

		String inputJson = mapToJson(order);
		HttpEntity<String> request = new HttpEntity<String>(inputJson, headers);
		String response = restTemplate.postForObject(url, request, String.class);
		assertEquals(true, response.contains("Order placed successfully"));
	}

	@Test
	public void testCancelOrderSuccess() throws Exception {

		String postUrl = "http://localhost:9090/orders";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Order order = new Order();
		order.setOrderId("OR-127");
		order.setOrderDate("2019-07-11");
		order.setProdId("PROD-127");
		order.setUserID("USR-127");
		String inputJson = mapToJson(order);
		HttpEntity<String> request = new HttpEntity<String>(inputJson, headers);
		String response = restTemplate.postForObject(postUrl, request, String.class);
		assertEquals(true, response.contains("Order placed successfully"));

		String cancelUrl = "http://localhost:9090/orders/cancel/OR-127";
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String response1 = restTemplate.getForObject(cancelUrl, String.class);
		assertEquals(true, response1.contains("Order with orderId"));

	}

	@Test
	public void testGetOrderSuccess() throws Exception {
		String url = "http://localhost:9090/orders/";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Order order = new Order();
		order.setOrderId("OR-124");
		order.setOrderDate("2019-07-11");
		order.setProdId("PROD-124");
		order.setUserID("USR-124");
		String inputJson = mapToJson(order);
		HttpEntity<String> request = new HttpEntity<String>(inputJson, headers);
		String response = restTemplate.postForObject(url, request, String.class);
		assertEquals(true, response.contains("Order placed successfully"));

		String url1 = "http://localhost:9090/orders/OR-124";
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<Order> response1 = restTemplate.getForEntity(url1, Order.class);
		assertEquals(HttpStatus.OK, response1.getStatusCode());

	}

	@Test
	public void testGetOrderFailure() throws Exception {
		String url1 = "http://localhost:9090/orders/hello/OR-122";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Order> response1 = null;
		try {
			response1 = restTemplate.getForEntity(url1, Order.class);
		} catch(Exception ex) {
			assertEquals(null, response1);
		}

	}

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
