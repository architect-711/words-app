package edu.architect_711.words.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.architect_711.words.entities.db.LanguageEntity;
import edu.architect_711.words.entities.mapper.LanguageMapper;
import edu.architect_711.words.repository.LanguageRepository;
import edu.architect_711.words.service.language.DefaultLanguageService;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LanguageServiceUnitTest {

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private DefaultLanguageService languageService;

    @Test
    public void should_return__langs_full_list() {
        final LanguageMapper languageMapper = new LanguageMapper();

        final List<LanguageEntity> list = List.of(
                        new LanguageEntity("lang1"),
                        new LanguageEntity("lang2"));

        given(languageRepository.findAll()).willReturn(list);

        assertThat(languageService.findAll().getBody()).isEqualTo(list.stream().map(languageMapper::toDto).toList());
    }

}
