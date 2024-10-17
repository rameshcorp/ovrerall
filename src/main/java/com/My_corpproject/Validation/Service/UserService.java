package com.My_corpproject.Validation.Service;


import com.My_corpproject.Validation.Dto.ResponseDto;
import com.My_corpproject.Validation.Dto.UserServiceReqDto;
import com.My_corpproject.Validation.Entities.LoginEmployee;

import java.util.List;

public interface UserService {
    ResponseDto createUser(UserServiceReqDto reqDto);
    List<LoginEmployee> getAllEmployees();
    ResponseDto loginUser(UserServiceReqDto reqDto);
    ResponseDto updateUser(UserServiceReqDto reqDto);
    ResponseDto deleteUser(UserServiceReqDto reqDto);
}
