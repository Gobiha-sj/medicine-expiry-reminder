
package com.example.medicinereminder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class NotificationCheckController {

    private final RestClient restClient;

    public NotificationCheckController() {
        this.restClient = RestClient.create(
                "http://localhost:8081"
        );
    }

    @GetMapping("/api/medicines/notification-check")
    public ResponseEntity<String> checkNotificationService() {

        String response = restClient.get()
                .uri("/api/notifications")
                .retrieve()
                .body(String.class);

        return ResponseEntity.ok(response);
    }
}
