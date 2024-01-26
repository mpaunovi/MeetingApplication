package com.mtg.app.services;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String generateToken(Authentication auth);
}
