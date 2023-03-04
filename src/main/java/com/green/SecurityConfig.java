package com.green;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.green.entity.Account;
import com.green.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired 
	AccountService accountService;
	@Autowired 
	BCryptPasswordEncoder pe;   //mã hóa mật khẩu
	
	//Cung cấp nguồn dữ liệu đăng nhập
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username->{
			try {
				Account user = accountService.findById(username);
				
				String password = pe.encode(user.getPassword());  //tải account thông qua username, sau đó lấy passwword đó mã hóa (pe)	
				String[] roles = user.getAuthorities().stream()   //lấy các vai trò của người dùng và đổi sang 1 mảng để tạo ra userdetail
						.map(er->er.getRole().getId())
						.collect(Collectors.toList()).toArray(new String[0]);
				
				//thông qua user với username để tạo ra userdetail để trả về 
				return User.withUsername(username).password(password).roles(roles).build();  //lấy từ csld: username-pw-roles
			} catch (NoSuchElementException e) {   //nếu user không tồn tại thì quăng ngoại lệ 
				throw new UsernameNotFoundException(username + "not found!");
			}
		});
	}
	
	//Phân quyền sử dụng
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/order/**").authenticated()   //chặn thao tác mua hàng nếu chưa đăng nhập
			.antMatchers("/admin/**").hasAnyRole("STAF","DIRE")
			.antMatchers("/rest/authorities").hasRole("DIRE")
			.anyRequest().permitAll();
		
		http.formLogin()
			.loginPage("/security/login/form")  //địa chỉ gọi đến form đăng nhập
			.loginProcessingUrl("/security/login")  //địa chỉ form submit tới
			
			.defaultSuccessUrl("/security/login/success",false)  //đăng nhập thành công chuyển tới đâu, 
			//false: là đăng nhập thành công ko nhất thiết phải quay về /security/login/success. Vậy nếu đăng nhập thành công nó sẽ trở lại trang trước đó
			
			.failureUrl("/security/login/error");  //lỗi thì chuyển đến trang lỗi, chuyển tới trang đăng nhập và báo sai thông tin đăng nhập thông qua message
		
		//check remember me
		http.rememberMe()
			.tokenValiditySeconds(86400);
		
		//đăng nhập rồi nhưng cố tình truy xuất đến những địa chỉ chưa được cấp quyền
		//chuyển tới trang đăng nhập và thông báo ko có quyền truy xuất
		http.exceptionHandling()
			.accessDeniedPage("/security/unauthorized");
		
		//đăng xuất
		http.logout()
			.logoutUrl("/security/logoff")
			.logoutSuccessUrl("/security/logoff/success");
	}
	
	//Cơ chế mã hoá mật khẩu
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Cho phép truy xuất REST API từ bên ngoài (domain khác)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
	}
}
