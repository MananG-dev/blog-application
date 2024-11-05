package com.blogapp.services;

import com.blogapp.entities.Category;
import com.blogapp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto createCategory(CategoryDto categoryDto);
    //update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    //get
    CategoryDto getCategory(Integer categoryId);
    //getall
    List<CategoryDto> getAllCategory();
    //delete
    void deleteCategory(Integer categoryId);
}
