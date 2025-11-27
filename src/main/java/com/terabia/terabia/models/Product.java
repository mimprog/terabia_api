package com.terabia.terabia.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private Double weight;

    @Column(nullable = true)
    private Double length;

    @Column(nullable = true)
    private Double width;

    @Column(nullable = true)
    private Double height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", referencedColumnName = "id", columnDefinition = "INTEGER")
    @JsonBackReference("productCategory-product")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private ProductCategory productCategory;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true, updatable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    @JsonManagedReference("product-images")
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    @JsonManagedReference("orderItem-product")
    List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonBackReference("supplier-products")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Supplier supplier;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    @JsonManagedReference("product-stocks")
    private List<Stock> stocks;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Nullable
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Nullable
    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    @Nullable
    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    @Nullable
    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}