package com.example.SpringApi;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    //<=============================UC3=======================>
    // Method to generate a greeting based on provided first and last name
    public String getGreeting(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return "Hello, " + firstName + " " + lastName + "!";
        } else if (firstName != null) {
            return "Hello, " + firstName + "!";
        } else if (lastName != null) {
            return "Hello, " + lastName + "!";
        } else {
            return "Hello World!";
        }
    }

    //<==========================UC1=============================>
    // Simpler method to return a basic greeting message (can be used if no name is provided)
    public String getGreeting() {
        return "Hello World!";
    }

    // A more personalized greeting with just one name (can be used for just first or last name)
    public String getPersonalizedGreeting(String name) {
        return "Hello, " + name + "!";
    }

}
