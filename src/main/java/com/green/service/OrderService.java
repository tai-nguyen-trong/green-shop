package com.green.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.green.entity.Order;

public interface OrderService {
	
	List<Order> findAll();  //Hiển thị tất cả đơn hàng theo khách hàng
	Order findById(Long id); //lấy 1 đơn hàng theo id
	Order create(JsonNode orderData);  //tạo đơn hàng(post)
	//Liệt kê những đơn hàng đã đặt qua username
	List<Order> findByUsername(String username);
	
	
	
	
	//update sp admin
	Order update(Order order);


	
	
	
	
	//sumary
	Long getToDayOrder();

	Long totalOrder();

	List<Object[]> getRevenueLast7Days();

}
