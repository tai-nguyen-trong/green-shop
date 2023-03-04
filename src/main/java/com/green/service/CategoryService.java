package com.green.service;

import java.util.List;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import com.green.entity.Category;

public interface CategoryService {
	
	//Lấy danh sách tất cả các loại danh mục
	List<Category> findAll();

	
	
	
	
	//sumary
	Category findById(String categoryID);
	
	Category create(Category category);

	Category update(Category category);

	void delete(String id);
	
	Page<Category> findAll(Pageable pageable);
}
