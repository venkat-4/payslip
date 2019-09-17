package com.cts.exception;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RunWith(SpringRunner.class)
public class CustomExceptionHandlerTest extends ResponseEntityExceptionHandler {

	@Mock
	public CustomExceptionHandler customExceptionHandler;

	@Mock
	WebRequest webRequest;

	CustomExceptionHandler customExceptionHandlerReal;
	@Mock
	MethodArgumentNotValidException methodArgumentNotValidException;

	ResponseEntity<Object> mockResponse;

	@Before
	public void setUp() {
		mockResponse = null;
		customExceptionHandlerReal = new CustomExceptionHandler();
	}

	@Test
	public void handleAllExceptionTest() {
		ResponseEntity<Object> res = customExceptionHandlerReal.handleAllException(new Exception(), webRequest);

		assertNotNull(res);
	}

	@Test
	public void handleInternalServerExceptionTest() {
		ResponseEntity<Object> res = customExceptionHandlerReal
				.handleInternalServerException(new InternalServerError("InternalServerException"), webRequest);

		assertNotNull(res);
	}

	@Test
	public void handleRecordNotFoundExceptionTest() {
		ResponseEntity<Object> res = customExceptionHandlerReal
				.handleRecordNotFoundException(new RecordNotFoundException("RecordNotFoundException"), webRequest);

		assertNotNull(res);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void methodArgumentNotValid() {
		mockResponse = new ResponseEntity(HttpStatus.BAD_REQUEST);
		Mockito.doReturn(mockResponse).when(customExceptionHandler).handleMethodArgumentNotValid(Mockito.any(),
				Mockito.any(HttpHeaders.class), Mockito.any(HttpStatus.class), Mockito.any());

		ResponseEntity<Object> ErrorResponse = customExceptionHandler.handleMethodArgumentNotValid(null,
				HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, null);
		assertThat(ErrorResponse, notNullValue());
		assertThat(ErrorResponse.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

}
