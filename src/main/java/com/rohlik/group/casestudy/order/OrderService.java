package com.rohlik.group.casestudy.order;

import com.rohlik.group.casestudy.product.Product;
import com.rohlik.group.casestudy.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final static Integer MINUTES_TO_ORDER_EXPIRATION = 30;

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order getOrderById(Integer orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public Order createNewOrder(List<OrderItem> orderItems) {
        List<OrderItem> result = validateProductQuantity(orderItems);
        if (result.isEmpty()) {
            Order order = new Order();
            order.setOrderItems(orderItems);
            return orderRepository.save(order);
        }
        return new Order(-1, result);
    }

    public boolean payOrder(Integer orderId) {
        Order order = orderRepository.getOrderById(orderId);
        long minutes = ChronoUnit.MINUTES.between(order.getCreatedAt(), LocalTime.now());
        if (minutes < MINUTES_TO_ORDER_EXPIRATION) {
            order.setPaid(true);
            orderRepository.save(order);
            return true;
        }
        cancelOrder(orderId);
        return false;
    }

    public void cancelOrder(Integer orderId) {
        Order order = orderRepository.getOrderById(orderId);
        List<Product> productsToUpdate = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = productRepository.getProductById(orderItem.getProductId());
            product.setQuantity(product.getQuantity() + orderItem.getQuantity());
            productsToUpdate.add(product);
        }
        productRepository.saveAll(productsToUpdate);
    }

    private List<OrderItem> validateProductQuantity(List<OrderItem> orderItems) {
        List<OrderItem> notEnoughOrderItems = new ArrayList<>();
        List<Product> productsToUpdate = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            Product product = productRepository.getProductById(orderItem.getProductId());
            if (orderItem.getQuantity() > product.getQuantity()) {
                orderItem.setQuantity(orderItem.getQuantity() - product.getQuantity());
                notEnoughOrderItems.add(orderItem);
            } else {
                product.setQuantity(product.getQuantity() - orderItem.getQuantity());
                productsToUpdate.add(product);
            }
        }
        if (notEnoughOrderItems.isEmpty()) {
            productRepository.saveAll(productsToUpdate);
        }
        return notEnoughOrderItems;
    }
}
