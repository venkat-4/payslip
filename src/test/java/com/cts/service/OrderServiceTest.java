package com.cts.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cts.model.Order;
import com.cts.repository.OrderRepository;


@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
	
	@InjectMocks
	OrderService orderService;
	
	@Mock
	OrderRepository orderRepository;
	
	@Test
	public void placeOrderTest(){
		Order order = new Order();
		order.setOrderDate("2019-09-12T12:01:20.457Z");
		order.setOrderId("123");
		order.setProdId("456");
		order.setUserID("67867");
		when(orderRepository.placeOrder(order)).thenReturn(order);
		orderService.placeOrder(order);
	}
	
	@Test
	public void cancelOrderTest(){
		Order order = new Order();
		order.setOrderId("123");
		when(orderRepository.cancelOrder("123")).thenReturn("Success");
		orderService.cancelOrder("123");
	}
	
	@Test
	public void getAllOrdersTest(){
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		order.setOrderId("123");
		Order order2 = new Order();
		order.setOrderId("345");
		orders.add(order);
		orders.add(order2);
		when(orderRepository.getAllOrders()).thenReturn(orders);
		assertNotNull(orderService.getAllOrders());
	}
	
	@Test
	public void getOrderByIdTest(){
		Order order = new Order();
		order.setOrderId("123");
		order.setProdId("45645");
		order.setUserID("6789687");
		when(orderRepository.getOrderById("123")).thenReturn(order);
		assertEquals("45645", orderService.getOrderById("123").getProdId());
		
	}
	
	@Test
	public void saveTest(){
		Order order = new Order();
		order.setOrderId("453123");
		order.setProdId("6783456");
		order.setUserID("67867");
		when(orderRepository.save(order)).thenReturn("Success");
		assertNotNull(orderService.save(order));
	}
	
	

}
