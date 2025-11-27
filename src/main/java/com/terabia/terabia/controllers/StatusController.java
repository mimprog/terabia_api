package com.terabia.terabia.controllers;

import com.terabia.terabia.models.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CrossOrigin(
        origins = "https://terabia.onrender.com", // Frontend URL
        allowedHeaders = "*", // Allow all headers
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS}, // Allowed methods
        allowCredentials = "true" // Allow credentials like cookies
)
@RestController
@RequestMapping("/api/v1/statuses")
public class StatusController {

    @GetMapping
    public ResponseEntity<List<Map<String, String>>> getStatuses() {
        List<Map<String, String>> statuses = Arrays.stream(Status.values())
                .map(status -> Map.of(
                        "name", status.name(),          // Enum name
                        "label", status.getLabel()))    // Enum label
                .toList();
        return ResponseEntity.ok(statuses);
    }
}
