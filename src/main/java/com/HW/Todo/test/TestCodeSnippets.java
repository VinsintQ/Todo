package com.HW.Todo.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCodeSnippets {



    @GetMapping("/hello")
    public String Hello(){
        System.out.println("All good so far");
        return "All good so far";
    }
}
