package edu.architect_711.words.unit.service;

import edu.architect_711.words.model.dto.WordLanguageDto;
import edu.architect_711.words.model.mapper.WordLanguageMapper;
import edu.architect_711.words.repository.WordLanguagesRepository;
import edu.architect_711.words.service.WordLanguageService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class WordLanguageServiceTest implements WordLanguageMapper {
    @Mock private WordLanguagesRepository repository;
    @InjectMocks private WordLanguageService service;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void throw_on_invalid_field() {
        WordLanguageDto dto = new WordLanguageDto("         ");

        Set<ConstraintViolation<WordLanguageDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }
}
