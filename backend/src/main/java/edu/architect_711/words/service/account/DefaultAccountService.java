package edu.architect_711.words.service.account;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import edu.architect_711.words.controller.service.AccountService;
import edu.architect_711.words.entities.db.AccountEntity;
import edu.architect_711.words.entities.db.JwtTokenEntity;
import edu.architect_711.words.entities.db.Role;
import edu.architect_711.words.entities.dto.AccountDto;
import edu.architect_711.words.entities.dto.JwtTokenDto;
import edu.architect_711.words.entities.mapper.EntityMapper;
import edu.architect_711.words.entities.mapper.JwtTokenMapper;
import edu.architect_711.words.repository.AccountRepository;
import edu.architect_711.words.repository.JwtTokenRepository;
import edu.architect_711.words.service.jwt_token.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class DefaultAccountService implements AccountService, LogoutHandler {
    private final static EntityMapper<JwtTokenEntity, JwtTokenDto> tokenMapper = new JwtTokenMapper();

    private final AccountRepository accountRepository;
    private final JwtTokenRepository jwtTokenRepository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenService jwtTokenService;

    @Override
    public ResponseEntity<JwtTokenDto> register(@Valid AccountDto accountDto) {
        AccountEntity accountEntity = accountRepository.save(defaultAccount(accountDto));

        return buildJwtOk(jwtTokenRepository.save(generateOfEntity(accountEntity)));
    }

    @Override
    public ResponseEntity<JwtTokenDto> login(@Valid AccountDto accountDto) {
        delegateToAuthManager(accountDto.getUsername(), accountDto.getPassword());

        AccountEntity accountEntity = accountRepository.safeFindByUsername(accountDto.getUsername());

        return buildJwtOk(gracefullySaveNewToken(accountEntity));
    }

    @Override
    public ResponseEntity<JwtTokenDto> refreshToken(HttpServletRequest request) {
        AccountEntity accountEntity = verifyToken(request, jwtTokenService::isRefreshValid);

        return buildJwtOk(gracefullySaveNewToken(accountEntity));
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        AccountEntity accountEntity = verifyToken(request, jwtTokenService::isAccessValid);

        jwtTokenRepository.deleteAllByUsername(accountEntity.getUsername());

        SecurityContextHolder.clearContext();
    }

    private JwtTokenEntity gracefullySaveNewToken(AccountEntity accountEntity) {
        jwtTokenRepository.deleteAllByUsername(accountEntity.getUsername());

        return jwtTokenRepository.save(generateOfEntity(accountEntity));
    }

    private Authentication delegateToAuthManager(String username, String password) {
        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(username,
                        password));

    }

    private interface TokenVerifier {
        boolean verify(String token, String username);
    }

    private AccountEntity verifyToken(HttpServletRequest request, TokenVerifier verifyTokenFunction) {
        String refreshToken = jwtTokenService.extractFromRequest(request);

        if (refreshToken == null)
            throw new AuthenticationServiceException("Token not found in the header.");

        String username = jwtTokenService.extractUsername(refreshToken);

        if (username == null)
            throw new AuthenticationServiceException("Username is null");

        AccountEntity accountEntity = accountRepository.safeFindByUsername(username);

        if (!jwtTokenService.isRefreshValid(refreshToken, accountEntity.getUsername()))
            throw new AuthenticationServiceException("Token is invalid");

        return accountEntity;
    }

    private ResponseEntity<JwtTokenDto> buildJwtOk(JwtTokenEntity jwtTokenEntity) {
        return ResponseEntity.ok().body(tokenMapper.toDto(jwtTokenEntity));
    }

    private AccountEntity defaultAccount(AccountDto accountEntity) {
        return AccountEntity.builder()
                .username(accountEntity.getUsername())
                .password(passwordEncoder.encode(accountEntity.getPassword()))
                .role(Role.USER)
                .build();
    }

    private JwtTokenEntity generateOfEntity(AccountEntity account) {
        return new JwtTokenEntity(
                jwtTokenService.generateAccessToken(account.getUsername()),
                jwtTokenService.generateRefreshToken(account.getUsername()),
                true,
                account);
    }

}
