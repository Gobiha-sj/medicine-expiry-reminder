
package com.example.medicinereminder.controller;

import com.example.medicinereminder.entity.Medicine;
import com.example.medicinereminder.service.MedicineService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    // Display the dashboard
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("medicines",
                medicineService.getAllMedicines());
        model.addAttribute("total",
                medicineService.countAll());
        model.addAttribute("expired",
                medicineService.countExpired());
        model.addAttribute("expiring",
                medicineService.countExpiringSoon());

        return "index";
    }

    // Display the add-medicine form
    @GetMapping("/medicines/new")
    public String showAddForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        model.addAttribute("pageTitle", "Add Medicine");
        return "medicine-form";
    }

    // Save a new medicine or update an existing one
    @PostMapping("/medicines/save")
    public String saveMedicine(
            Medicine medicine,
            RedirectAttributes redirectAttributes) {

        medicineService.saveMedicine(medicine);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Medicine saved successfully!");

        return "redirect:/";
    }

    // Display the edit form
    @GetMapping("/medicines/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        return medicineService.getMedicineById(id)
                .map(medicine -> {
                    model.addAttribute("medicine", medicine);
                    model.addAttribute("pageTitle", "Edit Medicine");
                    return "medicine-form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute(
                            "errorMessage",
                            "Medicine not found.");
                    return "redirect:/";
                });
    }

    // Delete a medicine
    @PostMapping("/medicines/delete/{id}")
    public String deleteMedicine(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        if (medicineService.getMedicineById(id).isPresent()) {
            medicineService.deleteMedicine(id);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Medicine deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Medicine not found.");
        }

        return "redirect:/";
    }

    // Display only medicines expiring within 7 days
    @GetMapping("/medicines/expiring")
    public String expiringMedicines(Model model) {
        model.addAttribute("medicines", medicineService.getExpiringSoon());
        return "expiring";
    }

    // Search medicines by name
    @GetMapping("/medicines/search")
    public String searchMedicines(
            @RequestParam String name,
            Model model) {

        model.addAttribute("medicines",
                medicineService.getAllMedicines().stream()
                        .filter(medicine ->
                                medicine.getName() != null
                                        && medicine.getName()
                                        .toLowerCase()
                                        .contains(name.toLowerCase()))
                        .toList());

        model.addAttribute("total", medicineService.countAll());
        model.addAttribute("expired", medicineService.countExpired());
        model.addAttribute("expiring",
                medicineService.countExpiringSoon());
        model.addAttribute("viewTitle", "Search Results");
        model.addAttribute("searchQuery", name);

        return "index";
    }
}
