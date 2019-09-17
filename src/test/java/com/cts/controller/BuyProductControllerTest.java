package com.cts.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.model.Product;
import com.cts.service.AuthService;
import com.cts.service.ProductDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BuyProductController.class)
public class BuyProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService studentService;
	
	@MockBean
	private ProductDetailsService orderService;
	
	@Test
	public void loginTest() throws Exception {
		String exampleCourseJson = "{\"password\":\"password\",\"userId\":\"user101\"}";

		Mockito.when(studentService.login(Mockito.anyObject())).thenReturn("User Looged in sucessfully");
		Mockito.when(orderService.addItem(Mockito.anyObject())).thenReturn("Product Added Successfully,Product Id:  101");
		Product product = new Product();
		product.setProdId("101");
		product.setProdName("prodName");
		product.setPrice("999");
		Mockito.when(orderService.getProductById(Mockito.anyObject())).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/placeorder").accept(MediaType.APPLICATION_JSON)
				.content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "User Order Placed Successfully!!!The user with ID => user101 has placed the Order with ID =>od-856429 having the prodcut with ID => 102";
		assertEquals(expected, result.getResponse().getContentAsString());
	}

}
