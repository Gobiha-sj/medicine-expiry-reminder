
package com.example.medicinereminder.repository;

import com.example.medicinereminder.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MedicineRepository
        extends JpaRepository<Medicine, Long> {

    // Existing derived query
    List<Medicine> findByExpiryDateBetween(
            LocalDate startDate,
            LocalDate endDate);

    // HQL/JPQL Query 1: Find expired medicines
    @Query("SELECT m FROM Medicine m WHERE m.expiryDate < :today")
    List<Medicine> findExpiredMedicines(
            @Param("today") LocalDate today);

    // HQL/JPQL Query 2: Find medicines expiring within 7 days
    @Query("SELECT m FROM Medicine m " +
            "WHERE m.expiryDate >= :today " +
            "AND m.expiryDate <= :endDate " +
            "ORDER BY m.expiryDate ASC")
    List<Medicine> findMedicinesExpiringSoon(
            @Param("today") LocalDate today,
            @Param("endDate") LocalDate endDate);
}
