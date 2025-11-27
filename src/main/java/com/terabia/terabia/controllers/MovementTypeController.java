package com.terabia.terabia.controllers;

import com.terabia.terabia.models.MovementType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/api/v1/movement-types")
public class MovementTypeController {

    @GetMapping
    public List<String> getMovementTypes() {
        // Return all the movement types as a list of strings
        return Arrays.asList(MovementType.values())
                .stream()
                .map(MovementType::name)  // Convert enum to string
                .toList();
    }

}
