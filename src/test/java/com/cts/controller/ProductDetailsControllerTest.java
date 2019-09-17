package com.cts.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.cts.model.Product;
import com.cts.repository.ProductDetailssRepo;
import com.cts.service.ProductDetailsService;
import com.cts.util.ProductExcelFile;

public class ProductDetailsControllerTest extends AbstractTest {

	@InjectMocks
	ProductDetailsController productDetailsController;

	@Mock
	private ProductDetailsService productDetailsService;

	@Mock
	private ProductExcelFile productExcelFile;

	@Mock
	private ProductDetailssRepo productDetailssRepo;

	@Mock
	private ProductDetailsService service;

	@Mock
	private RestTemplate restTemplate;

	@Before
	@Override
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testAddItem() {
		File file;
		file = new File("./src/main/resources/excel/product.xlsx");
		Product product = new Product();
		product.setProdId("112");
		product.setProdName("TV");
		product.setPrice("1000");
		String Response = "Product Added Successfully";
		when(productDetailsService.addItem(product)).thenReturn(Response);
		productDetailsController.addItem(product);
	}

	@Ignore("Test is ignored as a demonstration")
	@Test
	public void getProductByIdSuccess() throws Exception {
		String uri = "/products/12";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
	}

	@Test
	public void testRemoveItemFailure() throws Exception {
		when(service.removeItem("12")).thenReturn("Product not found in the system");
		productDetailsController.removeItem("12");
	}

	@Test
	public void testRemoveItem() {
		Product product = new Product();
		product.setProdId("1");
		product.setProdName("A");
		product.setPrice("10000");
		when(service.removeItem(product.getProdId())).thenReturn("Removed");
		productDetailsController.removeItem(product.getProdId());
	}

}
