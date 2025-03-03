package com.example.SpringApi;

import com.example.SpringApi.Entites.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.example.SpringApi.Repositories.GreetingRepository;

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
        Optional<Greeting> greetingOptional = greetingRepository.findById(id);
        return greetingOptional.map(Greeting::getMessage).orElse("Greeting not found!");
    }

    // Method to get all greetings
    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll(); // Returns all greetings in the repository
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
