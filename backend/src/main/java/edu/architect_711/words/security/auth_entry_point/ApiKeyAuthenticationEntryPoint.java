package edu.architect_711.words.security.auth_entry_point;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.architect_711.words.exception.BaseExceptionDescription;
import edu.architect_711.words.exception.DetailedAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import static edu.architect_711.words.exception.ExceptionType.AUTHENTICATION;

@Component @Slf4j @RequiredArgsConstructor
public class ApiKeyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("api key authentication entry point caught exception.", authException);

        makeResponse(response, authException);
    }

    private void makeResponse(final HttpServletResponse response, final AuthenticationException authenticationException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        final OutputStream outputStream = response.getOutputStream();

        BaseExceptionDescription responseReadyException = new BaseExceptionDescription("Authentication failed.",
                (authenticationException instanceof DetailedAuthenticationException)
                        ? authenticationException.getCause().getMessage()
                        : authenticationException.getMessage(),
                AUTHENTICATION);


        objectMapper.writeValue(outputStream, responseReadyException);
    }
    
}
