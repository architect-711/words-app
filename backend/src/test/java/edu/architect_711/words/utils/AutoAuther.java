package edu.architect_711.words.utils;

import edu.architect_711.words.controller.AuthenticationController;
import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.dto.TokenDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;

import java.util.Date;

public class AutoAuther {
    public static TokenDto auth(AuthenticationController authenticationController) {
        PersonDto personDto = new PersonDto(new Date().toString(), "password");

        ResponseEntity<?> register = authenticationController.register(personDto);
        if (!register.getStatusCode().is2xxSuccessful())
            throw new AuthenticationServiceException("Couldn't login new person");
        ResponseEntity<TokenDto> login = authenticationController.login(personDto);

        if (!login.getStatusCode().is2xxSuccessful())
            throw new AuthenticationServiceException("Couldn't login new person");

        return login.getBody();
    }
}
