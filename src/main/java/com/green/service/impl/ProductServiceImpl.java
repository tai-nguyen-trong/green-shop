package com.green.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.green.dao.ProductDAO;
import com.green.entity.Product;
import com.green.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductDAO pDao;

	//lấy tất cả sp
	@Override
	public List<Product> findAll() {
		return pDao.findAll();
	}
	
	//Xem chi tiết sản phẩm, lấy sp theo id
	@Override
	public Product findById(Integer id) {
		return pDao.findById(id).get();
	}
	
	//lấy sp theo danh mục
	//viết tryu vấn bên DAO vì ko hỗ trợ phương thức này
	@Override
	public List<Product> findByCategoryId(String cid) {
		return pDao.findByCategoryId(cid);
	}
	
	
	
	
	
	
	//thêm sp của admin và lưu vào csdl
	@Override
	public Product create(Product product) {
		return pDao.save(product);
	}
	
	//update sp của admin và lưu vào csdl
	@Override
	public Product update(Product product) {
		return pDao.save(product);
	}
	
	//delete sp của admin và lưu vào csdl
	@Override
	public void delete(Integer id) {
		//delete không cần trả gì về nên ko cần return và thêm void
		pDao.deleteById(id);
	}

	//loc san pham theo ten,gia
//	@Override
//	public List<Product> findByKeyword(String keyword) {
//		return pDao.findByKeyword(keyword);
//	}

	

	
	

	/* Summary  */
	@Override
	public Long getAvailable() {
		return pDao.getAvailable();
	}

	@Override
	public Long getTotalProduct() {
		return pDao.count();
	}

	@Override
	public List<Object[]> numberOfProductSoldByType() {
		return pDao.numberOfProductSoldByType();
	}

	@Override
	public List<Object[]> getPercentByCate() {
		return pDao.getPercentByCate();
	}

	@Override
	public List<Object[]> availableRate() {
		return pDao.availableRate();
	}

	@Override
	public List<Object[]> top10Product() {
		return pDao.top10Product();
	}

	

}