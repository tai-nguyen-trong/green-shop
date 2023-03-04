package com.green.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.green.entity.Category;
import com.green.service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
	@Autowired
	CategoryService categoryService;
	
	//lấy tất cả category hiển thị vào combobox trong tab Product Edition, dùng bên product-ctrl.js
	@GetMapping()
	public List<Category> getAll() {
		return categoryService.findAll();
	}
	
	
	
	
	//sumary
	@GetMapping("{id}")
	public Category getOne(@PathVariable("id")String id) {
		return categoryService.findById(id);
	}
	
	@PostMapping
	public Category create(@RequestBody Category category) {
		return categoryService.create(category);
	}
	
	@PutMapping("{id}")
	public Category update(@RequestBody Category category,@PathVariable("id")Integer id) {
		return categoryService.update(category);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")String id) {
		categoryService.delete(id);
	}
}
