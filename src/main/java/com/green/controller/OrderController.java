package com.green.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.service.OrderService;

@Controller
public class OrderController {
	@Autowired  // Để làm việc với Order
	OrderService orderService ;
	
	// Trong controller viết các phương thức và ánh xạ cho nó 1 địa chỉ url
	
	//Đến trang đặt hàng
	@RequestMapping("/order/checkout")
	public String checkout() {
		return "order/checkout";
	}
	
	//Liệt kê những đơn hàng đã đặt
	@RequestMapping("/order/list")
	public String list(Model model, HttpServletRequest request) {
		//request.getRemoteUser(): Lấy user đã đăng nhập vào
		String username = request.getRemoteUser();
		model.addAttribute("orders", orderService.findByUsername(username));
		return "order/list";
	}
	
	//Xem lại chi tiết đơn hàng được đặt, Nhận id thông qua đường dẫn, 
	//và dựa vào id để vào csdl lấy đơn hàng tương ứng với id đó để chuyển sang giao diện để hiển thị chi tiết đơn hàng
	@RequestMapping("/order/detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("order", orderService.findById(id));
		return "order/detail";
	}
}
