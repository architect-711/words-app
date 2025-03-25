package edu.architect_711.words.controller.service;

import edu.architect_711.words.entities.dto.AccountDto;
import edu.architect_711.words.entities.dto.JwtTokenDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<JwtTokenDto> register(@Valid AccountDto accountDto);

    ResponseEntity<JwtTokenDto> login(@Valid AccountDto accountDto);

    ResponseEntity<?> logout(HttpServletRequest request);

    ResponseEntity<JwtTokenDto> refreshToken(HttpServletRequest request);
}
