package com.HW.Todo.service;

import com.HW.Todo.exception.InformationNotFoundException;
import com.HW.Todo.model.Category;
import com.HW.Todo.model.Item;
import com.HW.Todo.model.User;
import com.HW.Todo.repository.CategoryRepository;
import com.HW.Todo.repository.ItemRepository;
import com.HW.Todo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

     ItemRepository itemRepository;
     CategoryRepository categoryRepository;

     @Autowired
    public void setItemService(ItemRepository itemRepository){
        this.itemRepository=itemRepository;
    }
    @Autowired
    public void setCategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
    public  Item createItem(Item itemObj, Long categoryId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId,getCurrentLoggedInUser().getId());
        if(category==null){
            throw new InformationNotFoundException(" not exist");
        }
        itemObj.setCategory(category);
        return   itemRepository.save(itemObj);

    }

    public List<Item> getItems(Long categoryId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId,getCurrentLoggedInUser().getId());
        if(category==null){
            throw new InformationNotFoundException(" not exist");
        }
        return itemRepository.findByCategoryId(categoryId);

    }

    public Item getItem( Long id) {
      Item item = itemRepository.findById(id).orElse(null);
      if (item==null){
          throw new InformationNotFoundException(" not exist");
      }
      return item;
    }

    public Item updateItem(Item itemObj, Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item==null){
            throw new InformationNotFoundException(" not exist");
        }
        item.setDescription(itemObj.getDescription());
        item.setDueDate(itemObj.getDueDate());
        return itemRepository.save(itemObj);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
