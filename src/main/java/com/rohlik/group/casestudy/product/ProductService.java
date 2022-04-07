package com.rohlik.group.casestudy.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Integer createNewProduct(Product product) {
        return productRepository.save(product).getId();
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
}
