package com.rohlik.group.casestudy.order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    Order getOrderById(Integer id);
}
