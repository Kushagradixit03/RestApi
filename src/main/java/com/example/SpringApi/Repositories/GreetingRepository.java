package com.example.SpringApi.Repositories;

import com.example.SpringApi.Entites.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {
    // The findAll() method is already provided by Spring Data JPA
}
