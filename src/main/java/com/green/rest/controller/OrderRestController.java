package com.green.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.green.entity.Order;
import com.green.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderRestController {
	@Autowired
	OrderService orderService;
	
	//Đặt hàng-liệt kê-xem chi tiết đơn hàng
	//post đơn hàng lên server để lưu vào database, server nhận đơn hàng đó bằng JsonNode
	//sau khi đặt hàng xong thì server sẽ trả về client 1 đơn hàng(orders)
	@PostMapping()
	public Order create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}
	
	
	
	
	
	
	//Lấy tất đơn hàng, dùng để hiển thị bên admin(order-ctrl.js)
		@GetMapping()
		public List<Order> getAll() {
			return orderService.findAll();
		}
		
		//Lấy 1 đơn hàng theo id
		@GetMapping("{id}")
		public Order getOne(@PathVariable("id") Long id) {
			return orderService.findById(id);
		}
		
		
		//Update đơn hàng của admin
		@PutMapping("{id}")
		public Order update(@PathVariable("id") Long id, @RequestBody Order order) {
			return orderService.update(order);
		}
	

}
