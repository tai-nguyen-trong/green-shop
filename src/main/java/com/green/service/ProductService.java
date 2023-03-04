package com.green.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.green.entity.Product;

public interface ProductService{
	
	
	//loc san pham theo ten,gia
//	List<Product> findByKeyword(String keyword);

	
	
	
	
	//Liên quan
	List<Product> findAll();  //Hiển thị tất cả sp

	Product findById(Integer id);  //Hiển thị thông tin sp theo id(chi tiết giỏ hàng)

	@Query("SELECT p FROM Product p WHERE p.category.id=?1")  //Lấy ra những sp có mã loại giống cái tham số ta truyền vào
	List<Product> findByCategoryId(String cid);  //Hiển thị thông tin theo mã loại(Cùng danh mục)

	
	
	
	
	
	//thêm sp của admin
	Product create(Product product);

	//update sp admin
	Product update(Product product);

	//delete sp admin, xóa nên không cần trả gì về nên dùng void
	void delete(Integer id);
	
	
	
	
	
	
	
	//sumary
	Long getAvailable();

	Long getTotalProduct();

	List<Object[]> numberOfProductSoldByType();

	List<Object[]> getPercentByCate();

	List<Object[]> availableRate();

	List<Object[]> top10Product();
	
}