package com.rohlik.group.casestudy.product;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Product getProductById (Integer id);

    List<Product> findAll();
}
