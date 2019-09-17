package com.cts.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.cts.controller.AbstractTest;
import com.cts.model.Order;
import com.cts.util.RWExcelOrder;

public class OrderRepositoryTest extends AbstractTest {

	@InjectMocks
	OrderRepository orderRepository;
	@Mock
	RWExcelOrder rWExcelOrder;

	@Test
	public void testPlaceOrder() {
		Order order = new Order();
		order.setOrderId("1");
		order.setProdId("1");
		order.setOrderDate("1");
		order.setUserID("1");
		when(rWExcelOrder.writeExcel(order)).thenReturn(order);
		orderRepository.placeOrder(order);
	}

	@Test
	public void testCancelOrder() {
		Order order = new Order();
		order.setOrderId("1");
		when(rWExcelOrder.cancelOrder(order.getOrderId())).thenReturn("cancelled");
		String res = orderRepository.cancelOrder(order.getOrderId());
		assertEquals(res, "cancelled");
	}

	@Test
	public void testGetAllOrders() {
		Order order = new Order();
		order.setOrderId("1");
		order.setProdId("1");
		order.setOrderDate("1");
		order.setUserID("1");
		Order order1 = new Order();
		order1.setOrderId("2");
		order.setProdId("2");
		order.setOrderDate("2");
		order.setUserID("2");
		List<Order> list = new ArrayList<Order>();
		list.add(order);
		list.add(order1);
		when(rWExcelOrder.readExcel()).thenReturn(list);
		List<Order> res = orderRepository.getAllOrders();
		assertNotEquals(res, null);
	}

	@Test
	public void testGetOrderById() {
		Order order = new Order();
		order.setOrderId("1");
		when(rWExcelOrder.getOrderById(order.getOrderId())).thenReturn(order);
		Order res = orderRepository.getOrderById(order.getOrderId());
		assertNotEquals(res, null);
	}

	@Test
	public void testSave() {
		Order order = new Order();
		order.setOrderId("1");
		order.setProdId("1");
		order.setOrderDate("1");
		order.setUserID("1");
		when(rWExcelOrder.writeOrderExcel(order)).thenReturn("User Order Placed Successfully");
		String res = orderRepository.save(order);
		assertEquals(res, "User Order Placed Successfully");
	}

}
