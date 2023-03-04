package com.green.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.green.entity.Account;


@Service
public interface AccountService {
	Account findById(String username);//đưa username vào để lấy account
	
	
	
	
	
	List<Account> getAdministrators();
	
	List<Account> findAll();
	
	Account create(Account account);

	Account update(Account account);
	
	Long getTotalAccount();

	List<Object[]> top10Customer();
	
	
	//sơn 
	 boolean delete(String username);
}
