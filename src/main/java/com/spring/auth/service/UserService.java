package com.spring.auth.service;


import com.spring.auth.model.User;
import com.spring.auth.payload.request.LoginRequest;
import com.spring.auth.payload.request.UserCreateRequest;
import com.spring.auth.payload.response.JwtResponse;

public interface UserService {

    JwtResponse authenticateUser(LoginRequest loginRequest);
    User createUser(UserCreateRequest userCreateRequest);

}
