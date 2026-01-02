package com.HW.Todo.service;

import com.HW.Todo.model.Category;
import com.HW.Todo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }


    public Category createCategory(Category categoryObj) {
        Category category = categoryRepository.save(categoryObj);
        return category;

    }
}
