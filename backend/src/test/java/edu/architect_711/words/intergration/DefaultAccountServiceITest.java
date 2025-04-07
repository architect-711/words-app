package edu.architect_711.words.intergration;

import edu.architect_711.words.entities.db.Role;
import edu.architect_711.words.entities.dto.AccountDto;
import edu.architect_711.words.entities.dto.JwtTokenDto;
import edu.architect_711.words.repository.AccountRepository;
import edu.architect_711.words.repository.JwtTokenRepository;
import edu.architect_711.words.service.account.DefaultAccountService;
import edu.architect_711.words.service.jwt_token.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(profiles = {"test", "postgres"})
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DefaultAccountServiceITest {

    @Autowired
    private DefaultAccountService accountService;

    @Autowired private AccountRepository accountRepository;
    @Autowired private JwtTokenRepository jwtTokenRepository;

    @Autowired
    private Validator validator;

    // assign only when saved. don't modify
    private JwtTokenDto savedToken;

    private final static AccountDto BASE =  new AccountDto(LocalDateTime.now().toString(), "password", null);

    @BeforeAll
    @Order(1)
    public void setup() {
        jwtTokenRepository.deleteAll();
        accountRepository.deleteAll(); // there are many table depend on it
    }

    @Test
    @Order(2)
    public void should_ok__register() {
        ResponseEntity<JwtTokenDto> register = accountService.register(BASE);

        assertTrue(register.getStatusCode().is2xxSuccessful());
        assertNotNull(register.getBody());
        assertTrue(accountRepository.findByUsername(BASE.getUsername()).isPresent());
    }

    @Test
    @Order(3)
    public void should_fail__invalid_request_dto() {
        final AccountDto invalidDto = new AccountDto("", null, Role.USER);

        Set<ConstraintViolation<AccountDto>> validate = validator.validate(invalidDto);

        if (validate.isEmpty())
            throw new ValidationException("fuck the spring. validation set is empty but it mut not");

        assertThrows(ConstraintViolationException.class, () -> accountService.register(invalidDto));
    }

    @Test
    @Order(4)
    @Transactional // saves from some stupid exception, either 'method returned void' or 'executing delete/update query' Fuck spring #2
    public void should_ok__login() {
        final AccountDto valid = new AccountDto(BASE.getUsername(), BASE.getPassword(), Role.USER);

        assertDoesNotThrow(() -> {
            ResponseEntity<JwtTokenDto> login = accountService.login(valid);

            assertNotNull((savedToken = login.getBody()));
        });
    }

    @Test
    @Order(5)
    public void should_fail__login_invalid_dto() {
        final AccountDto invalidDto = new AccountDto("", null, null);

        assertThrows(ConstraintViolationException.class, () -> {
            accountService.login(invalidDto);
        });
    }

    @Test
    @Order(6)
    @Transactional // saves from : Unexpected exception thrown: org.springframework.dao.InvalidDataAccessApiUsageException: Executing an update/delete query
    public void should_ok__refresh_token() throws InterruptedException {
        if (savedToken == null)
            throw new IllegalStateException("Saved token must not be null. Check out you don't run test seperatly");

        final HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);

        String expectedHeaderValue = savedToken.getRefreshToken();
        when(mockRequest.getHeader(JwtTokenService.DEFAULT_HEADER)).thenReturn(String.format("%s %s", JwtTokenService.DEFAULT_PREFIX, expectedHeaderValue));

        Thread.sleep(1_000); // pass some time to make tokens unique

        assertDoesNotThrow(() -> {
            final ResponseEntity<JwtTokenDto> response = accountService.refreshToken(mockRequest);
            assertTrue(response.getStatusCode().is2xxSuccessful());

            final JwtTokenDto dto = Objects.requireNonNull(response.getBody());

            assertNotEquals(savedToken.getAccessToken(), dto.getAccessToken());
            assertNotEquals(savedToken.getRefreshToken(), dto.getRefreshToken());

            assertNotNull((savedToken = response.getBody()));
        });
    }

    @Test
    @Order(7)
    public void should_fail__refresh_token_invalid() {
        final HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);

        when(mockRequest.getHeader(JwtTokenService.DEFAULT_HEADER)).thenReturn("blablabla");

        assertThrows(Exception.class, () -> accountService.refreshToken(mockRequest));
    }

    @Test
    @Order(8)
    @Transactional
    public void should_ok__logout() {
        final HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);

        when(mockRequest.getHeader(JwtTokenService.DEFAULT_HEADER)).thenReturn(String.format("%s %s", JwtTokenService.DEFAULT_PREFIX, savedToken.getAccessToken()));

        assertDoesNotThrow(() -> {
            ResponseEntity<?> logout = accountService.logout(mockRequest);
            assertTrue(logout.getStatusCode().is2xxSuccessful());
        });
    }

    @Test
    @Order(9)
    public void should_fail__logout_invalid_token() {
        final HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);

        when(mockRequest.getHeader(JwtTokenService.DEFAULT_HEADER)).thenReturn("fuck spring");

        assertThrows(Exception.class, () -> accountService.logout(mockRequest));
    }

    @AfterAll
    public void cleanup() {
        jwtTokenRepository.deleteAll();
        accountRepository.deleteAll();
    }

}
