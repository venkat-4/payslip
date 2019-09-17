package com.cts.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.model.User;
import com.cts.repository.AuthRepository;
import com.cts.service.AuthService;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class AuthServiceTest {
	
	
	@Mock
	AuthRepository authRepository;
	
	@InjectMocks
	AuthService authService;
	
	@Test
	public void loginTest(){
		
		User user = new User();
		user.setUserId("user101");
		user.setPassword("password");
		when(authRepository.login(user)).thenReturn("User Looged in sucessfully");
		String expected = "User Looged in sucessfully";
		String res = authService.login(user);
		assertEquals(expected, res);
		
	}
	
	@Test
	public void createUserTest(){
		
		User user = new User();
		user.setUserId("user101");
		user.setPassword("password");
		user.setFirstName("firstName");
		user.setLastName("t");
		when(authRepository.createUser(user)).thenReturn("User Registered Successfully");
		String expected = "User Registered Successfully";
		String res = authService.createUser(user);
		assertEquals(expected, res);
		
	}
	
	

}
