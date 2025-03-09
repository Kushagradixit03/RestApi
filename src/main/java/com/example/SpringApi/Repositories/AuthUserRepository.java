package com.example.SpringApi.Repositories;

import com.example.SpringApi.Models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    public AuthUser findByEmail(String email);

}
