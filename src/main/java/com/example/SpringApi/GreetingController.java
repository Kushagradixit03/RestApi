package com.example.SpringApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greet")
public class GreetingController {

    @GetMapping
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("{\"message\":\"Hello, world!\"}");
    }

    @PostMapping
    public ResponseEntity<String> createGreeting(@RequestBody String name) {
        return ResponseEntity.ok("{\"message\":\"Hello, " + name + "!\"}");
    }

    @PutMapping
    public ResponseEntity<String> updateGreeting(@RequestBody String name) {
        return ResponseEntity.ok("{\"message\":\"Updated greeting: Hello, " + name + "!\"}");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteGreeting(@RequestBody String name) {
        return ResponseEntity.ok("{\"message\":\"Goodbye, " + name + "!\"}");
    }
}
            