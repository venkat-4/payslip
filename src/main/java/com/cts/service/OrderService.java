package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.model.Order;
import com.cts.repository.OrderRepository;

@Service("orderService")
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Order placeOrder(Order order) {
		return orderRepository.placeOrder(order);
		
	}

	public String cancelOrder(String orderId) {
		return orderRepository.cancelOrder(orderId);
	}

	public List<Order> getAllOrders() {
		return orderRepository.getAllOrders();
	}

	public Order getOrderById(String orderId) {
		return orderRepository.getOrderById(orderId);
	}
	
	public String save(Order placeOrder) {
		return orderRepository.save(placeOrder);
	}
	
}
