package com.HW.Todo.controller;

import com.HW.Todo.model.Item;
import com.HW.Todo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api")
public class ItemController {

    ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService){
        this.itemService=itemService;
    }


    @PostMapping("/categories/{categoryId}/items")
    public Item createItem (@RequestBody Item itemObj, @PathVariable Long categoryId){
        return itemService.createItem(itemObj,categoryId);
    }

    @GetMapping("/categories/{categoryId}/items")
    public List<Item> getItems (@PathVariable Long categoryId) {
        return itemService.getItems(categoryId);
    }

    @GetMapping("/categories/items/{id}")
    public Item getItem (@PathVariable Long id) {
        return itemService.getItem(id);
    }

    @PutMapping("/categories/items/{id}")
    public Item updateItem (@RequestBody Item itemObj,@PathVariable Long id) {
        return itemService.updateItem(itemObj,id);
    }


    @DeleteMapping("/categories/items/{id}")
    public void deleteItem (@PathVariable Long id) {
         itemService.deleteItem(id);
    }
}
