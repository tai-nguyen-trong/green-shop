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
	BCryptPasswordEncoder pe;   //password encryption

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username->{
			try {
				Account user = accountService.findById(username);
				
				String password = pe.encode(user.getPassword());
				String[] roles = user.getAuthorities().stream()
						.map(er->er.getRole().getId())
						.collect(Collectors.toList()).toArray(new String[0]);

				return User.withUsername(username).password(password).roles(roles).build();
			} catch (NoSuchElementException e) {
				throw new UsernameNotFoundException(username + "not found!");
			}
		});
	}
	
	//Authorization of use
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/order/**").authenticated()   //Block purchases if not logged in
			.antMatchers("/admin/**").hasAnyRole("STAF","DIRE")
			.antMatchers("/rest/authorities").hasRole("DIRE")
			.anyRequest().permitAll();
		
		http.formLogin()
			.loginPage("/security/login/form")
			.loginProcessingUrl("/security/login")
			
			.defaultSuccessUrl("/security/login/success",false)
			//false: login success and Return to the previous page, true: return login form
			
			.failureUrl("/security/login/error");
		
		//check remember me
		http.rememberMe()
			.tokenValiditySeconds(86400);

		//no access rights
		http.exceptionHandling()
			.accessDeniedPage("/security/unauthorized");

		http.logout()
			.logoutUrl("/security/logoff")
			.logoutSuccessUrl("/security/logoff/success");
	}

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Allow REST API access from outside (different domain)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
	}
}
