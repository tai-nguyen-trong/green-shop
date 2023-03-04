package com.green.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.naming.directory.SearchControls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.entity.Product;
import com.green.service.ProductService;

@Controller
public class ProductController {
	//Tiêm ProductService, Chứa các phương thức để ta thực hiện những mong muốn trong controller
	@Autowired
	ProductService productService;
	
	
//	@GetMapping("/search")
//	public String Search(@Param("keyword") String keyword, Model model) {
//		System.out.println("keyword:" + keyword);
//		model.addAttribute("s", keyword);
//		return "product/hienThiSanPham";
//	}
	
	
	// Trong controller viết các phương thức và ánh xạ cho nó 1 địa chỉ url
	
	
	
	
	//Lấy tất cả hàng hóa nếu không có mã loại, nếu có mã loại thì lọc sp theo danh mục mã loại(tham số: @RequestParam)
	@GetMapping("/product/list")
	public String list(Model model, @RequestParam("cid") Optional<String> cid) {
		if(cid.isPresent()) {//Nếu có mã loại(isPresent())
			List<Product> list = productService.findByCategoryId(cid.get());
			model.addAttribute("items", list);
		}
		else {  //không có thì lấy tất cả
			List<Product> list = productService.findAll();  //lấy tất cả sp
			model.addAttribute("items", list);  //lấy list đưa vào model để chuyển sang giao diện hiện ra với tên "items"
		}
		return "product/hienThiSanPham";
	}
	

	
	//Xem chi tiết sản phẩm
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {  //lấy mã sp thông qua đối số PathVariable
		//Lấy sp theo mã
		Product item = productService.findById(id);
		model.addAttribute("item", item);
		return "product/chiTietSanPham-SanPhamLienQuan";
	}

}
