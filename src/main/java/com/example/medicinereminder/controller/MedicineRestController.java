
package com.example.medicinereminder.controller;

import com.example.medicinereminder.entity.Medicine;
import com.example.medicinereminder.service.MedicineService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineRestController {

    private final MedicineService medicineService;

    public MedicineRestController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    // GET: Get all medicines
    @GetMapping
    public List<Medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    // GET: Get one medicine by ID
    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(
            @PathVariable Long id) {

        return medicineService.getMedicineById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Add a new medicine
    @PostMapping
    public ResponseEntity<Medicine> addMedicine(
            @RequestBody Medicine medicine) {

        medicine.setId(null);

        Medicine saved = medicineService.saveMedicine(medicine);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT: Update an existing medicine
    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(
            @PathVariable Long id,
            @RequestBody Medicine updated) {

        return medicineService.getMedicineById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setCategory(updated.getCategory());
                    existing.setExpiryDate(updated.getExpiryDate());
                    existing.setQuantity(updated.getQuantity());

                    Medicine saved = medicineService.saveMedicine(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE: Delete a medicine
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {

        if (medicineService.getMedicineById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        medicineService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }

    // GET: Get medicines expiring within 7 days
    @GetMapping("/expiring")
    public List<Medicine> getExpiringMedicines() {
        return medicineService.getExpiringSoon();
    }

    @GetMapping("/expired")
    public List<Medicine> getExpiredMedicines() {
        return medicineService.getExpiredMedicines();
    }
}
