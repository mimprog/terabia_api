package com.terabia.terabia.controllers;

import com.terabia.terabia.models.Role;
import com.terabia.terabia.models.Supplier;
import com.terabia.terabia.models.User;
import com.terabia.terabia.repositories.SupplierRepository;
import com.terabia.terabia.repositories.UserRepository;
import com.terabia.terabia.request.*;
import com.terabia.terabia.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return supplierService.createSupplier(supplier);
    }

    @GetMapping
    public List <Supplier> getSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getSupplier(@PathVariable Integer id) {
        return supplierService.getSupplierById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Integer id) {
        System.out.println("deleting supplier" + id);
        supplierService.deleteSupplier(id);
    }

    @PutMapping("/promote/{userId}")
    public ResponseEntity<?> promoteToSupplier(
            @PathVariable Integer userId,
            @RequestBody Supplier supplierRequest) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        if (user.getRole() == Role.SUPPLIER) {
            return ResponseEntity.badRequest().body("User is already a supplier");
        }

        user.setRole(Role.SUPPLIER);

        Supplier supplier = new Supplier();
        supplier.setUser(user);
        supplier.setCreatedAt(LocalDateTime.now());
        supplier.setName(supplierRequest.getName());
        supplier.setAddress(supplierRequest.getAddress());
        supplier.setTaxId(supplierRequest.getTaxId());

        supplierRepository.save(supplier);
        userRepository.save(user);

        return ResponseEntity.ok("User promoted to supplier successfully");
    }

}
