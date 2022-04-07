package com.rohlik.group.casestudy.product;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/getAll")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/products/new")
    public Integer createNewProduct(@RequestBody Product product) {
        return productService.createNewProduct(product);
    }

    @DeleteMapping("/products/{productId}/delete")
    public void deleteProduct(@PathVariable("productId") Integer productId) {
        productService.deleteProduct(productId);
    }
}
