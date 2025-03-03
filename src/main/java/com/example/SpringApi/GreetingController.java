package com.example.SpringApi;

import com.example.SpringApi.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingService greetingService;

    // Inject the GreetingService via constructor injection
    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    // Endpoint to handle dynamic greeting based on user attributes
    @GetMapping
    public String getGreeting(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName
    ) {
        return greetingService.getGreeting(firstName, lastName);
    }
}
