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

import com.cts.service.AuthService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AuthController.class, secure = false)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService studentService;

	@Test
	public void loginTest() throws Exception {
		String exampleCourseJson = "{\"password\":\"password\",\"userId\":\"user101\"}";

		Mockito.when(studentService.login(Mockito.anyObject())).thenReturn("User Looged in sucessfully");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/login").accept(MediaType.APPLICATION_JSON)
				.content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "User Looged in sucessfully with user Id:user101";
		assertEquals(expected, result.getResponse().getContentAsString());
	}

	@Test
	public void createUserTest() throws Exception {

		String exampleCourseJson = "{\"firstName\":\"mano\",\"lastName\":\"r\",\"password\":\"password\",\"userId\":\"user105\"}";
		Mockito.when(studentService.createUser(Mockito.anyObject())).thenReturn("User Registered Successfully");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/createuser")
				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "User Registered Successfully";
		assertEquals(expected, result.getResponse().getContentAsString());
	}

}
