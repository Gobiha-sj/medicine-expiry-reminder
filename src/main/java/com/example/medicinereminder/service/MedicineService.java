
package com.example.medicinereminder.service;

import com.example.medicinereminder.entity.Medicine;
import com.example.medicinereminder.repository.MedicineRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    // Get all medicines
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    // Get one medicine by ID
    public Optional<Medicine> getMedicineById(Long id) {
        return medicineRepository.findById(id);
    }

    // Add or update a medicine
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    // Delete a medicine
    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }

    // Get medicines expiring within the next 7 days
    public List<Medicine> getExpiringSoon() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysLater = today.plusDays(7);

        return medicineRepository.findMedicinesExpiringSoon(
                today, sevenDaysLater);
    }

    // Count expired medicines
    public long countExpired() {
        return getExpiredMedicines().size();
    }

    // Count medicines expiring soon
    public long countExpiringSoon() {
        return getExpiringSoon().size();
    }

    // Count all medicines
    public long countAll() {
        return medicineRepository.count();
    }

    public List<Medicine> getExpiredMedicines() {
        return medicineRepository.findExpiredMedicines(LocalDate.now());
    }

}
