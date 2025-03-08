package edu.architect_711.words.controller.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import edu.architect_711.words.entities.dto.AccountDto;
import edu.architect_711.words.entities.dto.JwtTokenDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountService {
    ResponseEntity<JwtTokenDto> register(AccountDto person);

    ResponseEntity<JwtTokenDto> login(AccountDto person);

    void logout(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication);

    ResponseEntity<JwtTokenDto> refreshToken(HttpServletRequest request);
}
