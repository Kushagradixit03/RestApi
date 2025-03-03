package com.example.SpringApi.Controllers;

import com.example.SpringApi.Entites.Greeting;
import com.example.SpringApi.Services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
//<========================UC5===============================>
    // Endpoint to fetch a greeting message by its ID
    @GetMapping("/{id}")
    public String getGreetingById(@PathVariable Long id) {
        return greetingService.getGreetingById(id);
    }
//<=======================UC6===============================>
    // Endpoint to list all greeting messages

    @GetMapping("/all")
    public List<Greeting> getAllGreetings() {
        return greetingService.getAllGreetings();  // Returns all greetings
    }

    // Endpoint to update a greeting message by ID
    //<===================UC7==========================>
    @PutMapping("/{id}")
    public String updateGreeting(@PathVariable Long id, @RequestBody String newMessage) {
        return greetingService.updateGreeting(id, newMessage);  // Update greeting message
    }
}
