package com.cts.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.model.User;

@RunWith(SpringRunner.class)
public class RWExcelFileAuthTest {

	private RWExcelFileAuth rWExcelFileAuth;

	@Test
	public void readExcelTest() {

		rWExcelFileAuth = new RWExcelFileAuth();
		String res = rWExcelFileAuth.readExcel(getUser(), "./src/main/resources/excel/user.xlsx");
		String expected = "User Looged in sucessfully";
		assertEquals(expected, res);

	}

	@Test
	public void writeExcel() {

		rWExcelFileAuth = new RWExcelFileAuth();
		String res = rWExcelFileAuth.writeExcel(getUser(), "./src/main/resources/excel/user.xlsx");
		String expected = "User Registered Successfully";
		assertEquals(expected, res);

	}

	private User getUser() {
		User user = new User();
		user.setUserId("user112");
		user.setFirstName("giri");
		user.setLastName("t");
		user.setPassword("passwor");
		return user;
	}
}
