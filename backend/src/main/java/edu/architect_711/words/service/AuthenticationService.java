package edu.architect_711.words.service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.dto.TokenDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Role;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.model.mapper.TokenMapper;
import edu.architect_711.words.model.validation_groups.PersonValidationGroups;
import edu.architect_711.words.repository.TokenRepository;
import edu.architect_711.words.repository.safe.SafePersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service @Validated @RequiredArgsConstructor
public class AuthenticationService implements PersonMapper, TokenMapper {
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final SafePersonRepository safePersonRepository;
    private final TokenRepository tokenRepository;

    @Validated(PersonValidationGroups.Create.class)
    public ResponseEntity<?> register(@Valid PersonDto personDto) {
        Person person = toEntity(personDto, p -> {
            p.setPassword(passwordEncoder.encode(p.getPassword()));
            p.setRole(Role.USER);
        });

        safePersonRepository.save(person);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<TokenDto> login(@Valid PersonDto personDto) {
        Person person = safePersonRepository.findPersonByUsername(personDto.getUsername());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                personDto.getUsername(),
                personDto.getPassword(),
                person.getAuthorities()
        ));

        return buildOk(toDto(tokenRepository.save(tokenService.getUpdater().fullUpdate(person))));
    }

    public ResponseEntity<TokenDto> refreshToken(HttpServletRequest request) {
        String refreshToken = TokenService.Extractor.safeExtractFromHeader(request.getHeader("Authorization"));
        Person tokenOwner = safePersonRepository.findPersonByUsername(tokenService.getExtractor().extractUsername(refreshToken));

        if (!tokenService.getValidator().isRefreshValid(refreshToken, tokenOwner.getUsername()))
            throw new AuthenticationServiceException("Refresh token is invalid");

        return buildOk(toDto(tokenService.getUpdater().fullUpdate(tokenOwner)));
    }

    private <T> ResponseEntity<T> buildOk(T payload) {
        return ResponseEntity.ok(payload);
    }
}
