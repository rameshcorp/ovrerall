package com.My_corpproject.Validation.Dto;

import com.My_corpproject.Validation.Entities.LoginEmployee;
import lombok.Data;

@Data
public class UserServiceReqDto {
    private String phone;    // Phone number of the user
    private String email;    // Email of the user
    private String password; // Password of the user
    private String name;     // Name of the user

    // This method will return a LoginEmployee object with the details set
    public LoginEmployee getLoginEmployee() {
        LoginEmployee loginEmployee = new LoginEmployee();
        loginEmployee.setName(this.name);
        loginEmployee.setEmail(this.email);
        loginEmployee.setPhone(this.phone);
        loginEmployee.setPassword(this.password); // Use the password here
        loginEmployee.setActive(true);  // Assuming the user is active by default
        return loginEmployee;
    }
}
