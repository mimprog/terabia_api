package com.terabia.terabia.services;

import com.terabia.terabia.config.JwtService;
import com.terabia.terabia.models.*;
import com.terabia.terabia.repositories.*;
import com.terabia.terabia.models.*;
import com.terabia.terabia.repositories.*;
import com.terabia.terabia.request.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public List<OrderItem> getAllOrdersItem() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getOrderItemById(Integer id) {
        return orderItemRepository.findById(id);
    }

    public Order createOrder(String token, List<OrderItem> orderItems, String paymentMethod, CustomerRequest customerDetails) {
        try {
            String userId = jwtService.getUserIdFromToken(token);
            System.out.println("User ID from token: " + userId);

            Integer userIdInt;
            try {
                userIdInt = Integer.parseInt(userId);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid userId format in token", e);
            }

            // Fetch the USER by ID
            User user = userRepository.findById(userIdInt)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userIdInt));
            System.out.println("Found user: " + user.getId() + " - " + user.getEmail());

            // Find existing Customer for this User
            Customer customer = customerRepository.findByUser(user).orElse(null);

            boolean hasPlacedFirstOrder = customer != null && orderRepository.existsByCustomerId(customer.getId());
            System.out.println("Has placed first order: " + hasPlacedFirstOrder);

            if (customer == null) {
                System.out.println("Creating customer record for first-time order");
                customer = createCustomer(user, customerDetails);
            }

            double totalCost = orderItems.stream()
                    .mapToDouble(item -> {
                        Double price = item.getProduct().getPrice();
                        return (price != null ? price : 0.0) * item.getQuantity();
                    })
                    .sum();
            System.out.println("Total cost calculated: " + totalCost);

            // Create new Order
            Order newOrder = new Order();
            newOrder.setCustomer(customer);
            newOrder.setTotalCost(totalCost);
            newOrder.setPaymentMethod(paymentMethod);
            newOrder.setCreatedAt(LocalDateTime.now());
            newOrder.setUpdatedAt(LocalDateTime.now());

            System.out.println("Saving order...");
            Order savedOrder = orderRepository.save(newOrder);
            System.out.println("Order saved with ID: " + savedOrder.getId());

            // Process order items and update stock
            for (OrderItem item : orderItems) {
                Product product = item.getProduct();
                System.out.println("Processing product: " + product.getId() + " - " + product.getName());

                // FIXED: Handle multiple stock entries
                List<Stock> stocks = stockRepository.findAllByProductId(product.getId());
                if (stocks.isEmpty()) {
                    throw new RuntimeException("Stock not found for product ID: " + product.getId());
                }

                // Use the first stock entry (you can change this logic as needed)
                Stock stock = stocks.get(0);
                System.out.println("Found " + stocks.size() + " stock entries for product " + product.getId() + ", using the first one");

                int newStockQuantity = stock.getQuantity() - item.getQuantity();
                System.out.println("Current stock: " + stock.getQuantity() + ", Ordered: " + item.getQuantity() + ", New stock: " + newStockQuantity);

                if (newStockQuantity < 0) {
                    throw new RuntimeException("Not enough stock for product: " + product.getName());
                }
                stock.setQuantity(newStockQuantity);
                stockRepository.save(stock);

                item.setOrder(savedOrder);
                item.setCreatedAt(LocalDateTime.now());
                item.setUpdatedAt(LocalDateTime.now());
                OrderItem savedItem = orderItemRepository.save(item);
                System.out.println("Order item saved with ID: " + savedItem.getId());
            }

            System.out.println("=== ORDER CREATION COMPLETED ===");
            return savedOrder;

        } catch (Exception e) {
            System.err.println("ERROR in createOrder: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Method to create order item (for individual item creation)
    public OrderItem createOrderItem(Order order, Integer product, Integer quantity) {
        var product1 = productRepository.findById(product)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem newItem = new OrderItem();
        newItem.setProduct(product1);
        newItem.setQuantity(quantity);
        newItem.setCreatedAt(LocalDateTime.now());
        newItem.setUpdatedAt(LocalDateTime.now());
        newItem.setOrder(order);

        return orderItemRepository.save(newItem);
    }

    private Customer createCustomer(User user, CustomerRequest customerDetails) {
        Customer customer = new Customer();

        // Set required latitude/longitude (provide defaults if null)
        Double latitude = customerDetails.getLatitude() != null ? customerDetails.getLatitude() : 0.0;
        Double longitude = customerDetails.getLongitude() != null ? customerDetails.getLongitude() : 0.0;

        customer.setLatitude(latitude);
        customer.setLongitude(longitude);
        customer.setAddress(customerDetails.getAddress() != null ? customerDetails.getAddress() : "");
        customer.setCity(customerDetails.getCity() != null ? customerDetails.getCity() : "");
        customer.setState(customerDetails.getState() != null ? customerDetails.getState() : "");
        customer.setCountry(customerDetails.getCountry() != null ? customerDetails.getCountry() : "");
        customer.setPostalCode(customerDetails.getPostalCode() != null ? customerDetails.getPostalCode() : "");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setUser(user);

        return customerRepository.save(customer);
    }
}