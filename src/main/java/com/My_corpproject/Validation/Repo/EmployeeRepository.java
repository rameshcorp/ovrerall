package com.My_corpproject.Validation.Repo;

import com.My_corpproject.Validation.Entities.LoginEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<LoginEmployee, Long> {

    // Add a method to find by email or phone
    Optional<LoginEmployee> findByEmailAndPhone(String email, String phone);
    Optional<LoginEmployee>findByEmailAndPasswordAndPhoneAndName(String email, String phone, String name, String password);
    Optional<LoginEmployee> findByEmailAndPassword(String email, String password);

}