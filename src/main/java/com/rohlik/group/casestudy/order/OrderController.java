package com.rohlik.group.casestudy.order;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/get")
    public Order createNewOrder(Integer orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping("/orders/new")
    public Order createNewOrder(@RequestBody List<OrderItem> orderItems) {
        return orderService.createNewOrder(orderItems);
    }

    @PutMapping("/orders/{orderId}/pay")
    public boolean payOrder(@PathVariable("orderId") Integer orderId) {
        return orderService.payOrder(orderId);
    }

    @DeleteMapping("/orders/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") Integer orderId) {
        orderService.cancelOrder(orderId);
    }
}
