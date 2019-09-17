package com.cts.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.cts.model.Order;
import com.cts.repository.ProductDetailssRepo;
import com.cts.service.OrderService;
import com.cts.util.RWExcelOrder;

public class OrderControllerServiceTest extends AbstractTest {

	@Mock
	private OrderController orderController;
	
	@Mock
	private OrderService orderService;

	@Mock
	private RWExcelOrder orderExcelFile;

	@Mock
	private ProductDetailssRepo productDetailsRepo;

	@Before
	@Override
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testAddOrder() throws Exception {
		/*
		 * String url = "http://localhost:9090/orders"; RestTemplate restTemplate = new
		 * RestTemplate(); HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON); Order order = new
		 * Order(); order.setOrderId("OR-123"); order.setOrderDate("2019-07-11");
		 * order.setProdId("PROD-123"); order.setUserID("USR-123"); String inputJson =
		 * super.mapToJson(order); HttpEntity<String> request = new
		 * HttpEntity<String>(inputJson, headers); String response =
		 * restTemplate.postForObject(url, request, String.class); assertEquals(true,
		 * response.contains("Order placed successfully"));
		 */
		
		File file;
		file = new File("./src/main/resources/excel/order.xlsx");
		Order order = new Order();
		order.setOrderId("OR-123");
		order.setOrderDate("2019-07-11");
		order.setProdId("PROD-123");
		order.setUserID("USR-123");
		String Response = "Order placed successfully";
		when(orderService.placeOrder(order)).thenReturn(order);
		
	}

	@Test
	public void testCancelOrderSuccess() throws Exception {

		String postUrl = "http://localhost:9090/orders";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Order order = new Order();
		order.setOrderId("OR-123");
		order.setOrderDate("2019-07-11");
		order.setProdId("PROD-123");
		order.setUserID("USR-123");
		String inputJson = super.mapToJson(order);
		HttpEntity<String> request = new HttpEntity<String>(inputJson, headers);
		String response = restTemplate.postForObject(postUrl, request, String.class);
		assertEquals(true, response.contains("Order placed successfully"));

		String cancelUrl = "/orders/cancel/OR-123";
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(cancelUrl).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		assertNotNull(mvcResult.getResponse());
		int status = mvcResult.getResponse().getStatus();

		assertEquals(HttpStatus.OK.value(), status);

	}

	@Test
	public void testCancelOrderFailure() throws Exception {
		String uri = "/orders/cancel/xyz123";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}

	@Test
	public void testGetAllOrdersSuccess() throws Exception {
		String uri = "/orders";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Order[] orderlist = super.mapFromJson(content, Order[].class);
		assertNotNull(mvcResult.getResponse());
		assertTrue(orderlist.length > 0);
	}

	/*
	 * @Test public void testGetAllOrdersFailure() throws Exception { String uri =
	 * "/orders"; MvcResult mvcResult =
	 * mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.
	 * APPLICATION_JSON_VALUE)) .andReturn();
	 * 
	 * int status = mvcResult.getResponse().getStatus(); assertEquals(200, status);
	 * String content = mvcResult.getResponse().getContentAsString();
	 * assertTrue(content.length() == 2); }
	 */

	@Test
	public void testGetOrderSuccess() throws Exception {
		String url = "http://localhost:9090/orders";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Order order = new Order();
		order.setOrderId("OR-123");
		order.setOrderDate("2019-07-11");
		order.setProdId("PROD-123");
		order.setUserID("USR-123");
		String inputJson = super.mapToJson(order);
		HttpEntity<String> request = new HttpEntity<String>(inputJson, headers);
		String response = restTemplate.postForObject(url, request, String.class);
		assertEquals(true, response.contains("Order placed successfully"));

		String uri = "/orders/OR-123";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
	}

	@Test
	public void testGetOrderFailure() throws Exception {
		String uri = "/orders/or-abc-123";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}

}
