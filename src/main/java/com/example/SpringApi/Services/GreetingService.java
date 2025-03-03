package com.example.SpringApi.Services;

import com.example.SpringApi.Entites.Greeting;
import com.example.SpringApi.Repositories.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GreetingService {

    private final GreetingRepository greetingRepository;

    @Autowired
    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    // Method to generate a greeting based on provided first and last name
    public String getGreeting(String firstName, String lastName) {
        String message;
        if (firstName != null && lastName != null) {
            message = "Hello, " + firstName + " " + lastName + "!";
        } else if (firstName != null) {
            message = "Hello, " + firstName + "!";
        } else if (lastName != null) {
            message = "Hello, " + lastName + "!";
        } else {
            message = "Hello World!";
        }

        // Save the generated greeting message to the repository
        saveGreeting(message);

        return message;
    }

    // Save the greeting message to the database
    private void saveGreeting(String message) {
        Greeting greeting = new Greeting(message);
        greetingRepository.save(greeting); // Save the greeting to the database
    }

    // Method to fetch a greeting by ID
    public String getGreetingById(Long id) {
        // Use Optional to avoid null pointer exceptions
        Optional<Greeting> greetingOptional = greetingRepository.findById(id);

        // If the greeting exists, return the message; otherwise, return a "not found" message
        return greetingOptional.map(Greeting::getMessage).orElse("Greeting not found!");
    }

    // Simpler method to return "Hello World" and save it
    public String getGreeting() {
        String message = "Hello World!";
        saveGreeting(message);
        return message;
    }

    // A more personalized greeting with just one name (save it to the repository)
    public String getPersonalizedGreeting(String name) {
        String message = "Hello, " + name + "!";
        saveGreeting(message);
        return message;
    }
}
