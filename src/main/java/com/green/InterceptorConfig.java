package com.green;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.green.interceptor.GlobalInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	//Cấu hình để chặn các ủrl không cần thiết để chạy danh mục bên phải
	@Autowired
	GlobalInterceptor globalInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(globalInterceptor)
		//Chỉ ra những globalInterceptor mà ta muốn Interceptor này thực hiện
		.addPathPatterns("/product/list")  //thực hiện trừ dòng ở dưới
		.excludePathPatterns("/rest/**", "/admin/**", "/assets/**");  //không thực hiện
	}
}
