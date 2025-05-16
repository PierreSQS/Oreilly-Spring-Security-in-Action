package com.laurentiuspilca.ssia.controllers;

import com.laurentiuspilca.ssia.model.Product;
import com.laurentiuspilca.ssia.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/find")
    public List<Product> findProducts() {
        return productService.findProducts();
    }
}
