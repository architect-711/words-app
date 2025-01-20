package edu.architect_711.words.controller;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.dto.TokenDto;
import edu.architect_711.words.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/authentication") @RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody PersonDto personDto) {
        return authenticationService.register(personDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody PersonDto personDto) {
        return authenticationService.login(personDto);
    }

    @GetMapping("/refresh_token")
    public ResponseEntity<TokenDto> refreshToken(HttpServletRequest request) {
        return authenticationService.refreshToken(request);
    }
}
