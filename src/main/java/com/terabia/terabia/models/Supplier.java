package com.terabia.terabia.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String address;
    private String taxId;

    @OneToOne( fetch = FetchType.EAGER)
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference("user-supplier")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "supplier", orphanRemoval = false)
    @JsonManagedReference("supplier-products")
    private List <Product> products;

    public Integer getId() {
        return id;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("taxId")
    public String getTaxId() {
        return taxId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }
}
