package com.mtg.app.services;

import com.mtg.app.dtos.request.LoginDto;
import com.mtg.app.dtos.request.RegistrationDto;
import com.mtg.app.dtos.response.LoginResponseDto;
import com.mtg.app.dtos.response.RegistrationResponseDto;

public interface AuthenticationService {

    RegistrationResponseDto registerUser(RegistrationDto registrationDto);

    LoginResponseDto loginUser(LoginDto loginDto);
}
