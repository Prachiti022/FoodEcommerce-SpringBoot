package com.purehealthyeats.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.purehealthyeats.product.service.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/menu")
    public String showMenuPage(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "Menu";
    }

    @GetMapping("/product/{id}")
    public String showProductDetailPage(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findProductById(id));
        return "ProductDetail";
    }

    @GetMapping("/customize/{id}")
    public String showCustomizePage(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findProductById(id));
        return "Customize";
    }
}
