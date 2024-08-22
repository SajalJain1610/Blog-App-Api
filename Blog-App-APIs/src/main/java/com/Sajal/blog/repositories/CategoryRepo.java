package com.Sajal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sajal.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
