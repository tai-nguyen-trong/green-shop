package com.green.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.dao.AccountDAO;
import com.green.dao.AuthorityDAO;
import com.green.entity.Account;
import com.green.entity.Authority;
import com.green.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{
	@Autowired
	AuthorityDAO dao;
	
	@Autowired
	AccountDAO acdao;

	@Override
	public List<Authority> findAll() {
		return dao.findAll();
	}

	@Override
	public Authority create(Authority auth) {
		return dao.save(auth);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<Authority> findAuthoritiesOfAdministrators() {
		List<Account> accounts = acdao.getAdministrators();
		return dao.authoritiesOf(accounts);
	}


	
	
	/*Summary*/
	@Override
	public Long getTotalCustomer() {
		return dao.findAll().stream().filter(e->e.getRole().getName().equals("Customers")).count();
	}
}
