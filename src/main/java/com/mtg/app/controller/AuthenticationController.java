package com.mtg.app.controller;

import com.mtg.app.dtos.request.LoginDto;
import com.mtg.app.dtos.request.RegistrationDto;
import com.mtg.app.dtos.response.LoginResponseDto;
import com.mtg.app.dtos.response.RegistrationResponseDto;
import com.mtg.app.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> registerUser(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(authenticationService.registerUser(registrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authenticationService.loginUser(loginDto));
    }
}
