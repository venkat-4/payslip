package com.cts.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.model.User;
import com.cts.util.RWExcelFileAuth;

@RunWith(SpringRunner.class)
public class AuthRepositoryTest {

	@Mock
	RWExcelFileAuth rWExcelFileAuth;
	
	@InjectMocks
	AuthRepository authRepository;
	
	@Test
	public void loginTest(){
		User user = new User();
		user.setUserId("user101");
		user.setPassword("password");
		when(rWExcelFileAuth.readExcel(user,"./src/main/resources/excel/user.xlsx")).thenReturn("User Looged in sucessfully");
		String res = authRepository.login(user);
		String expected = "User Looged in sucessfully";
		assertEquals(expected, res);
	}
	
	@Test
	public void createUserTest(){
		User user = new User();
		user.setUserId("user101");
		user.setPassword("password");
		user.setFirstName("firstName");
		user.setLastName("t");
		when(rWExcelFileAuth.writeExcel(user,"./src/main/resources/excel/user.xlsx")).thenReturn("User Registered Successfully");
		String expected = "User Registered Successfully";
		String res = authRepository.createUser(user);
		assertEquals(expected, res);
	}
}
