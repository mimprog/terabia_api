package com.terabia.terabia.seeders;

import com.terabia.terabia.auth.AuthenticationService;
import com.terabia.terabia.auth.RegisterRequest;
import com.terabia.terabia.models.*;
import com.terabia.terabia.repositories.*;
import com.terabia.terabia.models.*;
import com.terabia.terabia.repositories.*;
import com.terabia.terabia.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.*;

@Configuration
@RequiredArgsConstructor
@Transactional
public class DataSeeder {

    private final UserService userService;
    private final AuthenticationService authService;

    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;

    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;


    @Bean
    CommandLineRunner seedDatabase() {
        return args -> {
            System.out.println("🌱 SEEDING DATABASE...");

            // USERS
            registerUserIfNotExists("admin@gmail.com", "Admin", "Admin", "password", "ADMIN");
            registerUserIfNotExists("manager@gmail.com", "Manager", "Manager", "password", "MANAGER");

            for (int i = 1; i <= 5; i++) {
                registerUserIfNotExists("user" + i + "@gmail.com", "User" + i, "Test", "password", "USER");
            }

            // SUPPLIERS
            if (supplierRepository.count() == 0) {
                for (int i = 1; i <= 3; i++) {
                    User user = registerUserIfNotExists("supplier" + i + "@gmail.com", "Supplier" + i, "Last", "password", "SUPPLIER");

                    Supplier supplier = new Supplier();
                    supplier.setName("Supplier " + i);
                    supplier.setAddress("Address " + i);
                    supplier.setTaxId("TAX-" + i);
                    supplier.setUser(user);
                    supplier.setCreatedAt(LocalDateTime.now());
                    supplier.setUpdatedAt(LocalDateTime.now());

                    supplierRepository.save(supplier);
                }
            }

            // CUSTOMERS
            if (customerRepository.count() == 0) {
                for (int i = 1; i <= 3; i++) {
                    User user = registerUserIfNotExists("customer" + i + "@gmail.com", "Customer" + i, "Lastname", "password", "CUSTOMER");

                    Customer customer = new Customer();
                    customer.setLatitude(41.0 + i);
                    customer.setLongitude(19.0 + i);
                    customer.setCity("City " + i);
                    customer.setCountry("Country");
                    customer.setAddress("Street " + i);
                    customer.setUser(user);
                    customer.setCreatedAt(LocalDateTime.now());
                    customer.setUpdatedAt(LocalDateTime.now());

                    customerRepository.save(customer);
                }
            }

            // CATEGORIES
            if (categoryRepository.count() == 0) {
                List<Category> categories = Arrays.asList(
                        new Category(null, "Electronics", "electronics.jpg", null, LocalDateTime.now(), LocalDateTime.now()),
                        new Category(null, "Home Appliances", "home.jpg", null, LocalDateTime.now(), LocalDateTime.now()),
                        new Category(null, "Food", "food.jpg", null, LocalDateTime.now(), LocalDateTime.now())
                );
                categoryRepository.saveAll(categories);
            }

            // PRODUCT CATEGORIES (SUBCATS)
            if (productCategoryRepository.count() == 0) {
                Category electronics = categoryRepository.findByName("Electronics").orElseThrow();
                Category food = categoryRepository.findByName("Food").orElseThrow();

                List<ProductCategory> pcs = Arrays.asList(
                        new ProductCategory(null, "Phones", "phone.jpg", electronics, null, LocalDateTime.now(), LocalDateTime.now()),
                        new ProductCategory(null, "Laptops", "laptop.jpg", electronics, null, LocalDateTime.now(), LocalDateTime.now()),
                        new ProductCategory(null, "Snacks", "snack.jpg", food, null, LocalDateTime.now(), LocalDateTime.now())
                );
                productCategoryRepository.saveAll(pcs);
            }

            // PRODUCTS
            if (productRepository.count() == 0) {
                ProductCategory phones = productCategoryRepository.findByName("Phones").orElseThrow();
                ProductCategory laptops = productCategoryRepository.findByName("Laptops").orElseThrow();

                Product p1 = new Product();
                p1.setName("iPhone 14");
                p1.setPrice(999.0);
                p1.setDescription("apple.jpg");
                p1.setProductCategory(phones);
                p1.setCreatedAt(LocalDateTime.now());
                p1.setUpdatedAt(LocalDateTime.now());

                Product p2 = new Product();
                p2.setName("DELL LATITUDE");
                p2.setPrice(1500.0);
                p2.setDescription("dell.jpg");
                p2.setProductCategory(laptops);
                p2.setCreatedAt(LocalDateTime.now());
                p2.setUpdatedAt(LocalDateTime.now());

                productRepository.saveAll(List.of(p1, p2));
            }

            // ======================
            // 📌 STOCKS (5–8 records)
            // ======================
            if (stockRepository.count() == 0) {
                User admin = userRepository.findByEmail("admin@gmail.com").orElseThrow();
                List<Product> products = productRepository.findAll();

                List<Stock> stocks = new ArrayList<>();

                for (int i = 0; i < 6; i++) {
                    Stock stock = new Stock();
                    stock.setProduct(products.get(i % products.size()));
                    stock.setQuantity(10 + i);
                    stock.setSku("SKU-" + (1000 + i));
                    stock.setStatus(Status.IN_STOCK);
                    stock.setReorderLevel(5);
                    stock.setMovementType(MovementType.ARRIVAL);
                    stock.setMovementReason("Initial stock seeding");
                    stock.setCreatedBy(admin);
                    stock.setUpdatedBy(admin);
                    stock.setCreatedAt(LocalDateTime.now());
                    stock.setUpdatedAt(LocalDateTime.now());
                    stocks.add(stock);
                }

                stockRepository.saveAll(stocks);
            }

            // =====================
            // 📌 ORDERS + ORDER ITEMS
            // =====================
            if (orderRepository.count() == 0) {

                User customer = userRepository.findByEmail("customer1@gmail.com").orElseThrow();
                List<Product> products = productRepository.findAll();

                for (int i = 0; i < 5; i++) {

                    Order order = new Order();
                    order.setCustomer(customer.getCustomer());
                    order.setPaymentMethod("MOBILE_MONEY");
                    order.setCreatedAt(LocalDateTime.now());
                    order.setUpdatedAt(LocalDateTime.now());

                    double total = 0;
                    List<OrderItem> items = new ArrayList<>();

                    for (int j = 0; j < 2; j++) {
                        Product p = products.get((i + j) % products.size());

                        OrderItem item = new OrderItem();
                        item.setOrder(order); // important
                        item.setProduct(p);
                        item.setQuantity(1 + j);
                        item.setCreatedAt(LocalDateTime.now());
                        item.setUpdatedAt(LocalDateTime.now());

                        items.add(item);
                        total += p.getPrice() * item.getQuantity();
                    }

                    order.setOrderItems(items);
                    order.setTotalCost(total);

                    orderRepository.save(order); // cascade saves items
                }
            }


            System.out.println("🎉 SEEDING COMPLETE");
        };
    }

    private User registerUserIfNotExists(String email, String firstname, String lastname, String password, String role) {
        return userService.findByEmail(email)
                .orElseGet(() -> {
                    RegisterRequest request = new RegisterRequest();
                    request.setEmail(email);
                    request.setFirstname(firstname);
                    request.setLastname(lastname);
                    request.setPassword(password);
                    request.setRole(role);
                    authService.register(request);
                    return userService.findByEmail(email).orElseThrow();
                });
    }
}
