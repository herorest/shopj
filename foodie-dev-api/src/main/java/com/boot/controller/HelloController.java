package com.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class HelloController {

    @GetMapping("/h")
    public Object hello(){
        return "hello world";
    }
}
