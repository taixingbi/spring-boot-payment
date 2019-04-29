package com.example.demo.controller;
import com.example.demo.model.Products;
import com.example.demo.repository.ProductRepository;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<Products> all() {
        return productRepository.findAll();
    }


}
