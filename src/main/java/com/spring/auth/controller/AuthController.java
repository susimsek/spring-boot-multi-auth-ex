package com.spring.auth.controller;



import com.spring.auth.config.Profiles;
import com.spring.auth.payload.request.LoginRequest;
import com.spring.auth.payload.response.JwtResponse;
import com.spring.auth.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Profile(Profiles.JWT_AUTH)
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
@RequiredArgsConstructor
@RequestMapping("/versions/1/auth")
public class AuthController {

    UserService userService;

    @ApiOperation(value = "Authenticate a User",response = JwtResponse.class )
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }

}
