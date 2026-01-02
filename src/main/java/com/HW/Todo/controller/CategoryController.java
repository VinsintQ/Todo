package com.HW.Todo.controller;


import com.HW.Todo.model.Category;
import com.HW.Todo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category categoryObj){
        Category category = categoryService.createCategory(categoryObj);

        return category;

    }

    @GetMapping("/categories")
    public List<Category> getCategories(){
        List<Category> category = categoryService.getCategories();

        return category;

    }

    @GetMapping("/categories/{id}")
    public Optional<Category> getCategory(@PathVariable Long id){

        return categoryService.getCategory(id);



    }

    @PutMapping("/categories/{id}")
    public Category updateCategory(@RequestBody Category categoryObj,@PathVariable Long id){
        Category category = categoryService.Update(categoryObj,id);

        return category;

    }

    @DeleteMapping("/categories/{id}")
    public Category DeleteCategory(@PathVariable Long id){
        Category category = categoryService.delete(id);

        return category;

    }

}
