package com.green.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.green.service.CategoryService;

@Component
public class GlobalInterceptor implements HandlerInterceptor{
	@Autowired
	CategoryService categoryService;
	
	//Hiển thị danh mục sp
	//truy vấn toàn bộ danh mục bỏ vào request và hiện qua giao diện
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// postHandle: sau khi thực hiện 1 phương thức bên trong controller xong thì postHandle mới chạy
		request.setAttribute("cates", categoryService.findAll());  // tạo 1 attribute là cates và lấy tất cả các loại bỏ vào đó:categoryService
	}
}