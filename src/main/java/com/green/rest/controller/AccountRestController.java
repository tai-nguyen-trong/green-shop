package com.green.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.green.entity.Account;
import com.green.service.AccountService;

@CrossOrigin("*")
@RestController
@RequestMapping("rest")
public class AccountRestController {
	@Autowired private AccountService accountService;
	
	@GetMapping("accounts")
	public List<Account>getAccounts(@RequestParam("admin")Optional <Boolean>admin){
		if(admin.orElse(false)) {
			return accountService.getAdministrators();
		}
		return accountService.findAll();
	}
	
	@PostMapping("accountsManage")
	public Account create(@RequestBody Account account) {
		return accountService.create(account);
	}
	
	@PutMapping("accounts/{id}")
	public Account update(@RequestBody Account account,@PathVariable("id")String username) {
		return accountService.update(account);
	}
	
	
	@DeleteMapping("accounts/{id}")
	public boolean delete(@PathVariable("id")String username) {
		return accountService.delete(username);
	}
}
