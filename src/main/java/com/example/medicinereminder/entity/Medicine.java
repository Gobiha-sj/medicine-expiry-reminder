
package com.example.medicinereminder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private LocalDate expiryDate;
    private int quantity;

    public Medicine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Transient
    public String getStatus() {
        if (expiryDate == null) {
            return "Unknown";
        }

        LocalDate today = LocalDate.now();

        if (expiryDate.isBefore(today)) {
            return "Expired";
        }

        if (!expiryDate.isAfter(today.plusDays(7))) {
            return "Expiring Soon";
        }

        return "Safe";
    }

    @Transient
    public long getDaysLeft() {
        if (expiryDate == null) {
            return 0;
        }

        return ChronoUnit.DAYS.between(
                LocalDate.now(), expiryDate
        );
    }
}
