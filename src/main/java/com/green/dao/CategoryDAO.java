package com.green.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.entity.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, String>{

}