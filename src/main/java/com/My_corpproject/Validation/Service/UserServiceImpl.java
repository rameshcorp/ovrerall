package com.My_corpproject.Validation.Service;
import com.My_corpproject.Validation.Dto.ResponseDto;
import com.My_corpproject.Validation.Dto.UserServiceReqDto;
import com.My_corpproject.Validation.Entities.LoginEmployee;
import com.My_corpproject.Validation.Repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<LoginEmployee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public ResponseDto createUser(UserServiceReqDto reqDto) {
        LoginEmployee employee = reqDto.getLoginEmployee(); // Use the corrected method name


        // Validate input
        ResponseDto validationResponse = validateUser(employee);
        if (!validationResponse.isSuccess()) {
            return validationResponse; // Return validation error
        }

        try {
            employeeRepository.save(employee);
            return new ResponseDto("Employee created successfully", true);
        } catch (DataIntegrityViolationException e) {
            return new ResponseDto("Error: Phone number or email already exists", false);
        } catch (Exception e) {
            return new ResponseDto("Error: " + e.getMessage(), false);
        }
    }

    // Validation methods
    private ResponseDto validateUser(LoginEmployee employee) {
        String phoneNumber = employee.getPhone();
        if (!isValidPhoneNumber(phoneNumber)) {
            return new ResponseDto("Error: Phone number must be exactly 10 digits", false);
        }

        String email = employee.getEmail();
        if (!isValidEmail(email)) {
            return new ResponseDto("Error: Email must be a valid Gmail address", false);
        }

        String password = employee.getPassword();
        if (!isValidPassword(password)) {
            return new ResponseDto("Error: Password must be at least 10 characters long, with at least one lowercase letter, one uppercase letter, and one number", false);
        }

        return new ResponseDto("Validation successful", true); // Success
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.endsWith("@gmail.com");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 10 &&
                password.matches(".*[a-z].*") && // contains at least one lowercase letter
                password.matches(".*[A-Z].*") && // contains at least one uppercase letter
                password.matches(".*[0-9].*");   // contains at least one number
    }

    @Override
    public ResponseDto loginUser(UserServiceReqDto reqDto) {
        try {
            LoginEmployee employee = reqDto.getLoginEmployee(); // Use the corrected method name
            // Get the LoginEmployee from reqDto

            // Check for employee by email, password, phone, and name
            Optional<LoginEmployee> employeeOptional = employeeRepository.findByEmailAndPasswordAndPhoneAndName(
                    employee.getEmail(),
                    employee.getPassword(),
                    employee.getPhone(),
                    employee.getName()
            );

            if (employeeOptional.isPresent()) {
                return new ResponseDto("Login successful", true);
            } else {
                return new ResponseDto("Login failed: Incorrect email, password, phone, or name", false);
            }
        } catch (Exception e) {
            return new ResponseDto("Error: " + e.getMessage(), false);
        }
    }

    @Override
    public ResponseDto updateUser(UserServiceReqDto reqDto) {
        try {
            // Query the employee by email and phone
            Optional<LoginEmployee> employeeOptional = employeeRepository.findByEmailAndPhone(reqDto.getEmail(), reqDto.getPhone());

            if (employeeOptional.isPresent()) {
                LoginEmployee employee = employeeOptional.get();

                // Update the employee's details
                employee.setName(reqDto.getName());  // Update name
                employee.setEmail(reqDto.getEmail());  // Update email
                employee.setPhone(reqDto.getPhone());  // Update phone
                employee.setPassword(reqDto.getPassword()); // Update password

                employeeRepository.save(employee); // Save updated employee

                return new ResponseDto("User details updated successfully", true);
            } else {
                return new ResponseDto("Update failed: Invalid user", false);
            }
        } catch (Exception e) {
            return new ResponseDto("Error: " + e.getMessage(), false);
        }
    }

    @Override
    public ResponseDto deleteUser(UserServiceReqDto reqDto) {
        try {
            // Get the LoginEmployee from reqDto
            LoginEmployee employee = reqDto.getLoginEmployee(); // Use the corrected method name


            // Use the email and password from the employee object for deletion
            Optional<LoginEmployee> employeeOptional = employeeRepository.findByEmailAndPassword(employee.getEmail(), employee.getPassword());

            if (employeeOptional.isPresent()) {
                employeeRepository.delete(employeeOptional.get()); // Delete the user
                return new ResponseDto("Account Deleted", true);
            } else {
                return new ResponseDto("Delete failed: User not found", false);
            }
        } catch (Exception e) {
            return new ResponseDto("Error: " + e.getMessage(), false);
        }
    }

}