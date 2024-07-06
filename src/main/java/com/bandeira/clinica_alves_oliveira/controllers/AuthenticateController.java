package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.LoginResponse;
import com.bandeira.clinica_alves_oliveira.dtos.RegisterDTO;
import com.bandeira.clinica_alves_oliveira.dtos.UserRequest;
import com.bandeira.clinica_alves_oliveira.models.User;
import com.bandeira.clinica_alves_oliveira.repositories.UserRepository;
import com.bandeira.clinica_alves_oliveira.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth")
public class AuthenticateController {
    @Autowired
    private AuthenticationManager autheticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserRequest userRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userRequest.username(), userRequest.password());
        var auth = this.autheticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        if(this.repository.findByUsername(registerDTO.username()) != null)
            return ResponseEntity.ok().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.username(), encryptedPassword, registerDTO.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
