//package com.green.service.impl;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.green.dao.OrderDetailDAO;
//import com.green.service.OrderDetailService;
//import com.green.util.XDate;
//
//
//
//@Service
//public class OrderDetailServiceImpl implements OrderDetailService {
//
//	@Autowired private OrderDetailDAO daoDetail;
//	@Autowired private XDate xdate;
//	
//	@Override
//	public Double getTodayIncome() {
//		return daoDetail.findAll().stream().filter(e->e.getOrder().getCreateDate().toString().equals(xdate.convertToPattern(new Date(), "yyyy-MM-dd"))).mapToDouble(item->item.getPrice()*item.getQuantity()).sum();
//	}
//	@Override
//	public Double getTotalIncome() {
//		return daoDetail.findAll().stream().mapToDouble(item->item.getPrice()*item.getQuantity()).sum();
//	}
//
//}
