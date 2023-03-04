package com.green.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.dao.AccountDAO;
import com.green.entity.Account;
import com.green.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired private AccountDAO adao;

	@Override
	public Account findById(String username) {
		return adao.findById(username).get();
	}

	//lấy ra những account có 1 trong 2 vai trò 'DIRE','STAF'
	@Override
	public List<Account> getAdministrators() {
		return adao.getAdministrators();
	}

	@Override
	public List<Account> findAll() {
		return adao.findAll();
	}
	
	@Override
	public Account create(Account account) {
		return adao.save(account);
	}

	@Override
	public Account update(Account account) {
		return adao.save(account);
	}
	/*Summary*/

	@Override
	public Long getTotalAccount() {
		return adao.count();
	}

	
	@Override
	public List<Object[]> top10Customer() {
		return adao.top10Customer();
	}

	
	//sơn
	@Override
	public boolean delete(String username) {
		Account getAccount = findById(username);
		if (Objects.isNull(getAccount)) {
			return false;
		}
		adao.delete(getAccount);
		return true;
	}
	
}
