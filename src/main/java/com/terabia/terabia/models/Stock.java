package com.terabia.terabia.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="stocks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
   // @JsonIgnoreProperties({"stocks"}) // Prevent circular reference when serializing stocks
    @JsonBackReference("product-stocks")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = true)
    @JsonBackReference("user-created-stock")
    private User createdBy;  // User who created or is managing this stock

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    @JsonBackReference("user-updated-stock")
    private User updatedBy;  // User who last updated this stock

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, unique = true)
    private String sku;

    @Enumerated(EnumType.STRING)  // Store the enum as a string in the database
    @Column(nullable = false)
    private Status status;

    @Column
    private Integer reorderLevel;

    // Enum for Stock Movement Types (e.g., Added, Reduced, Transferred)
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private MovementType movementType;

    // Reason for the stock movement (optional)
    @Column(nullable = true)
    private String movementReason;

    public Integer getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }


    @Column(nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;


    public Status getStatus() {
        return status;
    }
    

    public String getSku() {
        return sku;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public String getMovementReason() {
        return movementReason;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public void setMovementReason(String movementReason) {
        this.movementReason = movementReason;
    }
}

