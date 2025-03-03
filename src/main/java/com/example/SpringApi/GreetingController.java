package com.example.SpringApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingService greetingService;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    // Handle GET requests with optional query parameters for firstName and lastName
    @GetMapping
    public String getGreeting(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName
    ) {
        // If both firstName and lastName are provided, delegate to the appropriate service method
        if (firstName != null || lastName != null) {
            return greetingService.getGreeting(firstName, lastName);
        } else {
            // If no parameters, return the default greeting and save it
            return greetingService.getGreeting();
        }
    }

    // Personalized greeting
    @GetMapping("/personalized")
    public String getPersonalizedGreeting(@RequestParam String name) {
        return greetingService.getPersonalizedGreeting(name);
    }
}
