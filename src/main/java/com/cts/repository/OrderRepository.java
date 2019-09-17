/**
 * 
 * This class is used to give Order details
 * 
 * @author 764432
 *
 */

package com.cts.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cts.model.Order;
import com.cts.util.RWExcelOrder;

@Repository("OrderRepository")
public class OrderRepository {

	@Autowired
	private RWExcelOrder rWExcelOrder;

	/**
	 * OrderRepository() is used for initializing the rWExcelOrder variable.
	 * 
	 * @param rWExcelOrder
	 */
	public OrderRepository(RWExcelOrder rWExcelOrder) {
		super();
		this.rWExcelOrder = rWExcelOrder;
	}

	/**
	 * It will write the order into excel
	 * 
	 * @param order
	 * @return
	 */
	public Order placeOrder(final Order order) {
		return rWExcelOrder.writeExcel(order);
	}

	/**
	 * It will cancel the order by using orderId.
	 * 
	 * @param orderId
	 * @return String
	 */
	public String cancelOrder(final String orderId) {
		return rWExcelOrder.cancelOrder(orderId);
	}

	/**
	 * It will get list of orders.
	 * 
	 * @return List
	 */
	public List<Order> getAllOrders() {
		return rWExcelOrder.readExcel();
	}

	/**
	 * It will get the order by using orderId
	 * 
	 * @param orderId
	 * @return Order
	 */
	public Order getOrderById(final String orderId) {
		return rWExcelOrder.getOrderById(orderId);
	}

	/**
	 * It will save the order
	 * 
	 * @param placeOrder
	 * @return String
	 */
	public String save(final Order placeOrder) {
		return rWExcelOrder.writeOrderExcel(placeOrder);
	}
}
