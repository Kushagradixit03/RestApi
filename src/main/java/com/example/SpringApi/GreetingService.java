package com.example.SpringApi;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    // Method that returns a simple greeting message
    public String getGreeting() {
        return "Hello World";
    }

    // Method that returns a personalized greeting
    public String getPersonalizedGreeting(String name) {
        return "Hello, " + name + "!";
    }
}
