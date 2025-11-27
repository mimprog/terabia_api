package com.terabia.terabia.controllers;

import com.terabia.terabia.models.*;
import com.terabia.terabia.models.Order;
import com.terabia.terabia.models.OrderItem;
import com.terabia.terabia.models.Product;
import com.terabia.terabia.repositories.ProductRepository;
import com.terabia.terabia.request.CustomerRequest;
import com.terabia.terabia.request.OrderWithCustomerRequest;
import com.terabia.terabia.request.OrderRequest;
import com.terabia.terabia.request.OrderItemRequest;
import com.terabia.terabia.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders") // Add this class-level mapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/items")
    public List<OrderItem> getAllOrdersItem() {
        return orderService.getAllOrdersItem();
    }

    @GetMapping("/items/{id}")
    public Optional<OrderItem> getOrderItemById(@PathVariable Integer id) {
        return orderService.getOrderItemById(id);
    }

    @PostMapping // This will map to POST /api/v1/orders
    public Order createOrder(@RequestBody OrderWithCustomerRequest request,
                             @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        OrderRequest orderRequest = request.getOrderRequest();
        CustomerRequest customerRequest = request.getCustomerRequest();

        // Convert OrderItemRequest to OrderItem with products
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : orderRequest.getOrderItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + itemRequest.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItems.add(orderItem);
        }

        return orderService.createOrder(token, orderItems, orderRequest.getPaymentMethod(), customerRequest);
    }
}