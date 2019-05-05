package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @RequestMapping("/api/image")
    public String images(){

        return "this is image test";
    }

}
