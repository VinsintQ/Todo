package com.HW.Todo.repository;

import com.HW.Todo.model.Category;
import com.HW.Todo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    Item findByName(String recipeName);
    List<Item> findByCategoryId(Long categoryId);
    Item findByUserIdAndName(Long userId, String itemName);

}
