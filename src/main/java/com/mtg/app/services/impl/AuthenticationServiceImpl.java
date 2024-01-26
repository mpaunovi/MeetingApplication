package com.mtg.app.services.impl;

import com.mtg.app.dtos.ImportDto;
import com.mtg.app.dtos.request.LoginDto;
import com.mtg.app.dtos.request.RegistrationDto;
import com.mtg.app.dtos.response.LoginResponseDto;
import com.mtg.app.dtos.response.RegistrationResponseDto;
import com.mtg.app.models.Import;
import com.mtg.app.models.Role;
import com.mtg.app.models.User;
import com.mtg.app.repository.ImportRepository;
import com.mtg.app.repository.RoleRepository;
import com.mtg.app.repository.UserRepository;
import com.mtg.app.services.AuthenticationService;
import com.mtg.app.services.ImportService;
import com.mtg.app.services.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ImportService importService;
    private final ImportRepository importRepository;


    @Override
    public RegistrationResponseDto registerUser(RegistrationDto registrationDto) {

        String username = registrationDto.getUsername();
        String email = registrationDto.getEmail();
        String encodedPassword = encoder.encode(registrationDto.getPassword());

        Optional<User> existingUser = userRepository.findByUsernameOrEmail(username, email);

        if (existingUser.isPresent()) {

            User user = existingUser.get();
            List<ImportDto> imports = importService.getAllImports(user.getUserId());

            for (ImportDto importDto : imports) {
                Import newImport = new Import();
                newImport.setUser(user);
                newImport.setTitle(importDto.getTitle());
                newImport.setContent(importDto.getContent());
                importRepository.save(newImport);
            }

        } else {
            Role userRole = roleRepository.findByAuthority("USER").get();
            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);

            userRepository.save(User.builder()
                    .userId(UUID.randomUUID())
                    .username(username)
                    .email(email)
                    .password(encodedPassword)
                    .authorities(authorities)
                    .build());
        }

        return new RegistrationResponseDto(username, email);
    }

    @Override
    public LoginResponseDto loginUser(LoginDto loginDto) {
        try {
            User user = userRepository.findByUsername(loginDto.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );

            String token = tokenService.generateToken(auth);
            return new LoginResponseDto(loginDto.getUsername(), user.getUserId(), token);

        } catch (AuthenticationException e) {
            return new LoginResponseDto(null, null, "");
        }
    }

}
