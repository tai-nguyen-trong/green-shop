package com.green.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.green.dao.CategoryDAO;
import com.green.entity.Category;
import com.green.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	CategoryDAO cDao;
	
	//lấy tất cả các danh mục sp(category)
	@Override
	public List<Category> findAll(){
		return cDao.findAll();
	}
	
	
	
	//sumary
	@Override
	public Category findById(String categoryID) {
		return cDao.findById(categoryID).get();
	}

	@Override
	public Category create(Category category) {
		return cDao.save(category);
	}

	@Override
	public Category update(Category category) {
		return cDao.save(category);
	}

	@Override
	public void delete(String id) {
		cDao.deleteById(id);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return cDao.findAll(pageable);
	}
	
}
