package com.green.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping({"/", "/home/index"})
	public String home() {
		return "layout/index";  
	}
	
//	@RequestMapping({"/", "/home/index"})
//	public String home() {
//		return "redirect:/product/list";  
//		//layout/index
//	}
	
	@RequestMapping({"/admin", "/admin/home/index"})
	public String admin() {
		return "redirect:/assets/admin/index.html";
	}
}
