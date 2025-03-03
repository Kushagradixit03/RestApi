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

    // Endpoint that returns a simple greeting message
    @GetMapping
    public String getGreeting() {
        return greetingService.getGreeting();
    }

    // Endpoint that returns a personalized greeting
    @GetMapping("/{name}")
    public String getPersonalizedGreeting(@PathVariable String name) {
        return greetingService.getPersonalizedGreeting(name);
    }
}
