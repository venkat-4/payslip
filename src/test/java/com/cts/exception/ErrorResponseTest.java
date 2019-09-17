package com.cts.exception;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ErrorResponseTest {

	private ErrorResponse errorResponse;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void errorResponsetest() {
		errorResponse = new ErrorResponse();
		errorResponse = new ErrorResponse("message", new ArrayList());
		List list = new ArrayList();
		errorResponse.setDetails(list);
		errorResponse.setMessage("message");
		assertEquals(list, errorResponse.getDetails());
		assertEquals("message", errorResponse.getMessage());

	}

}
