package edu.architect_711.words.controller;

import edu.architect_711.words.controller.service.AccountService;
import edu.architect_711.words.entities.dto.AccountDto;
import edu.architect_711.words.entities.dto.JwtTokenDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/registration")
    public ResponseEntity<JwtTokenDto> register(@RequestBody AccountDto accountDto) {
        return accountService.register(accountDto);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody AccountDto accountDto) {
        return accountService.login(accountDto);
    }

    @GetMapping("/fresh_token")
    public ResponseEntity<JwtTokenDto> refreshToken(HttpServletRequest request) {
        return accountService.refreshToken(request);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return accountService.logout(request);
    }

}
