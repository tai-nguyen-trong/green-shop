package com.green.service;

import java.util.List;

import com.green.entity.Authority;

public interface AuthorityService {
	List<Authority> findAll();
	
	Authority create(Authority auth);
	
	void delete(Integer id);
	
	List<Authority> findAuthoritiesOfAdministrators();
	
	
	
	//sumary
	Long getTotalCustomer();

}
