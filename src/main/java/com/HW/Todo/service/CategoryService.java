package com.HW.Todo.service;

import com.HW.Todo.exception.InformationNotFoundException;
import com.HW.Todo.model.Category;
import com.HW.Todo.model.User;
import com.HW.Todo.repository.CategoryRepository;
import com.HW.Todo.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    public Category createCategory(Category categoryObject){

        Category category = categoryRepository.findByUserIdAndName(
                CategoryService.getCurrentLoggedInUser().getId(), categoryObject.getName());

        if(category!=null){
            throw new RuntimeException("category with name "+category.getName()+" already exist");
        }
        else {
            categoryObject.setUser(getCurrentLoggedInUser());
            return categoryRepository.save(categoryObject);
        }

    }

    public List<Category> getCategories(){
        System.out.println("Service calling getCategories -->");
        return categoryRepository.findByUserId(getCurrentLoggedInUser().getId());
    }


    public Optional<Category> getCategory(Long categoryId) {
        System.out.println("Service calling get category");
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()){
            return categoryRepository.findById(categoryId);
        }else {
            throw new InformationNotFoundException("category with id "+categoryId +"Not found");
        }

    }

    public  Category Update(Category categoryObj,Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category==null) {
            throw new InformationNotFoundException("not found");
        }
        if (!category.getUser().getId().equals(getCurrentLoggedInUser().getId())){
            return null;
        }
        category.setDescription(categoryObj.getDescription());
        return categoryRepository.save(category);

    }

    public Category delete(Long id) {

        Category category = categoryRepository.findById(id).orElse(null);
        if (!category.getUser().getId().equals(getCurrentLoggedInUser().getId())){
            return null;
        }
        if (category == null) {
            throw new RuntimeException("Category not found with name: " + id);
        }

        categoryRepository.delete(category);
        return category;
    }

}
