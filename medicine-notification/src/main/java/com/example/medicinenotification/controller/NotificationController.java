
package com.example.medicinenotification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NotificationController {

    @GetMapping("/api/notifications")
    public Map<String, String> getNotification() {
        return Map.of(
                "service", "Medicine Notification Service",
                "message", "Notification service is running successfully!"
        );
    }
}
