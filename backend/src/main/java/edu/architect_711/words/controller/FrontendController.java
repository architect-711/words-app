package edu.architect_711.words.controller;

import edu.architect_711.words.controller.service.WordService;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.entities.mapper.WordMapper;
import edu.architect_711.words.repository.WordRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FrontendController {
    private static final WordMapper wordMapper = new WordMapper();

    private final WordService wordService;
    private final WordRepository wordRepository;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/words")
    public String words(Model model) {
        model.addAttribute("words", wordService.read(10, 0).getBody());

        return "words";
    }

    @PostMapping("/words")
    public String saveNewOne(@Valid WordDto wordDto, BindingResult result, Model model) {
        if (result.hasErrors())
            result.failOnError(IllegalArgumentException::new);

        wordDto.setId(null); // wordService would insult, because relies on @Validated(...class)
        wordDto.setLocalDateTime(null);

        wordService.create(wordDto);

        return "redirect:/words";
    }

    @GetMapping("/words/{id}")
    public String getById(@PathVariable Long id, Model model) {
        final WordEntity entity = wordRepository.safeFindWordById(id);

        model.addAttribute("word", wordMapper.toDto(entity));

        return "word_card";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id, Model model) {
        wordRepository.deleteById(id);

        return "redirect:/words";
    }

}
