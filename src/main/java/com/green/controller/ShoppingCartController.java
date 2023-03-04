package com.green.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShoppingCartController {
	// Trong controller viết các phương thức và ánh xạ cho nó 1 địa chỉ url
	
	//Xem trang giỏ hàng
	@RequestMapping("/cart/view")
	public String view() {
		return "cart/view";
	}
}
