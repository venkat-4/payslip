package com.cts.util;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.model.Product;
import com.cts.model.User;

@RunWith(SpringRunner.class)
public class RWExcelFileTest {
	
	@InjectMocks
	RWExcelFile rWExcelFile;
	
	@Test
	public void readExcelTest() {
		String filepath = "./src/main/resources/excel/login.xlsx";
		 rWExcelFile.readExcel(filepath);
	}
	
	@Test
	public void writeExcelTest() {
		User user = new User();
		user.setFirstName("Sri");
		user.setLastName("RaM");
		user.setPassword("123yyd");
		user.setUserId("testUser");
		 assertEquals("User Registered Successfully", rWExcelFile.writeExcel(user));
	}
	
	 /*@Test(expected = Exception.class)
	 public void writeExcelTestException() {
		 User user = new User();
		 rWExcelFile.writeExcel(user);
	 }*/

}
