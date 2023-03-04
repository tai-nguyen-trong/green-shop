package com.green.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.dao.OrderDAO;
import com.green.dao.OrderDetailDAO;
import com.green.entity.Order;
import com.green.entity.OrderDetail;
import com.green.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDAO dao;
	
	@Autowired
	OrderDetailDAO ddao;
	
	@Override
	public List<Order> findAll() {
		return dao.findAll();
	}
	 //tạo và lưu thông tin đã đặt hàng vào dtb	
	@Override
	public Order create(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();
	
		Order order = mapper.convertValue(orderData, Order.class);
		dao.save(order); 
	
	//	TypeReference<List<OrderDetail>> type = new TypeReference<>() {
	//	};
		TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
		};
		List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type).stream()
				.peek(d -> d.setOrder(order)).collect(Collectors.toList());
		ddao.saveAll(details);
	
		return order;
	}
	
	//lấy đơn hàng theo id
	@Override
	public Order findById(Long id) {
		return dao.findById(id).get();
	}
	//lấy đơn hàng qua username
	@Override
	public List<Order> findByUsername(String username) {
		return dao.findByUsername(username);
	}

	
	
	
	
	
	
	
	
	//update đơn hang của admin và lưu vào csdl
		@Override
		public Order update(Order order) {
			return dao.save(order);
		}
	
		

	
	


	
	
	
	
	
	
	
	
	/* Summary section */
	
	@Override
	public Long getToDayOrder() {
		return dao.getTodayOrder();
	}
	@Override
	public Long totalOrder() {
		return dao.count();
	}
	@Override
	public List<Object[]> getRevenueLast7Days() {
		return dao.getRevenueLast7Days();
	}
}
