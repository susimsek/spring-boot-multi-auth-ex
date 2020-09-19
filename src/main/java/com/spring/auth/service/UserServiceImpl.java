package com.spring.auth.service;

import com.spring.auth.exception.role.RoleNotFoundException;
import com.spring.auth.exception.user.EmailAlreadyExistsException;
import com.spring.auth.exception.user.UsernameAlreadyExistsException;
import com.spring.auth.model.Role;
import com.spring.auth.model.User;
import com.spring.auth.model.enumerated.RoleName;
import com.spring.auth.payload.request.LoginRequest;
import com.spring.auth.payload.request.UserCreateRequest;
import com.spring.auth.payload.response.JwtResponse;
import com.spring.auth.repository.RoleRepository;
import com.spring.auth.repository.UserRepository;
import com.spring.auth.security.UserDetailsImpl;
import com.spring.auth.security.jwt_auth.util.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    AuthenticationManager authenticationManager;
    JwtTokenUtil jwtTokenUtil;
    UserRepository userRepository;
    RoleRepository roleRepository;
    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;


    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtTokenUtil.generateJwtToken(userPrincipal);

        return new JwtResponse(jwt);
    }

    @Override
    public User createUser(UserCreateRequest userCreateRequest) {

        if (userRepository.existsByUsername(userCreateRequest.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User user=modelMapper.map(userCreateRequest, User.class);
        if(user.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException());

        user.setRole(role);

        user=userRepository.save(user);
        return user;
    }
}
