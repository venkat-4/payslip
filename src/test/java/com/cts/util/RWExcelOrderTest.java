package com.cts.util;

import static org.junit.Assert.*;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.model.Order;

@RunWith(SpringRunner.class)
public class RWExcelOrderTest {
	
	@InjectMocks
	RWExcelOrder rWExcelOrder;
	
	@Test
	public void readExcelTest() {
		assertNotNull(rWExcelOrder.readExcel().size());
	}
	
	@Test
	public void writeExcelTest() {
		Order order1 = new Order();
		order1.setOrderId("123");
		order1.setProdId("G4234");
		order1.setUserID("test");
		assertEquals("123", rWExcelOrder.writeExcel(order1).getOrderId());
	}
	
	@Test
	public void cancelOrderTest() {
		Order order1 = new Order();
		order1.setOrderId("123");
		order1.setProdId("G4234");
		order1.setUserID("test");
		assertEquals("123", rWExcelOrder.cancelOrder(order1.getOrderId()));
	}
	
	@Test
	public void getOrderByIdTest() {
		Order order1 = new Order();
		order1.setOrderId("123");
		order1.setProdId("G4234");
		order1.setUserID("test");
		order1.setOrderDate("2019-09-12T12:01:20.457Z");
		assertNotEquals(order1, rWExcelOrder.getOrderById(order1.getOrderId()));
	}
	
	@Test
	public void writeOrderExcelTest() {
		Order order1 = new Order();
		order1.setOrderId("123");
		order1.setProdId("G4234");
		order1.setUserID("test");
		order1.setOrderDate("2019-09-12T12:01:20.457Z");
		assertNotEquals("User Order Placed Successfully!!! ", rWExcelOrder.writeOrderExcel(order1));
	}

}
