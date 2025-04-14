package edu.architect_711.words.controller;

import edu.architect_711.words.controller.service.LanguageService;
import edu.architect_711.words.controller.service.WordService;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.entities.mapper.WordMapper;
import edu.architect_711.words.repository.WordRepository;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Validated
@RequiredArgsConstructor
public class FrontendController {
    private static final WordMapper wordMapper = new WordMapper();

    private final WordService wordService;
    private final WordRepository wordRepository;
    private final LanguageService languageService;

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping(path = "/save_new", consumes = {"application/x-www-form-urlencoded"})
    public String saveNew(@RequestParam Map<String, String> map) {
        wordService.create(new WordDto(null, map.get("title"), map.get("translation"), map.get("description"), map.get("language"), null));

        return "redirect:/words";
    }

    @GetMapping("/new")
    public String newOne(Model model) {
        model.addAttribute("word", new WordDto());
        model.addAttribute("langs", languageService.findAll().getBody());

        return "new_word";
    }

    @GetMapping("/words")
    public String words(
            @RequestParam(defaultValue = "5", name = "size") int size,
            @RequestParam(defaultValue = "0", name = "page") int page,
            Model model
    ) {
        model.addAttribute("words", wordService.read(size, page).getBody());
        model.addAttribute("size", size);
        model.addAttribute("page", page);

        return "words";
    }

    @PostMapping(path = "/word", consumes = {"application/x-www-form-urlencoded"})
    public String findByTitle(
            @RequestParam Map<String, String> map,
            Model model
    ) {
        model.addAttribute("words", wordService.findByTitle(map.getOrDefault("title", "")).getBody());
        model.addAttribute("size", 5);
        model.addAttribute("page", 0);

        return "words";
    }

    @PostMapping("/update")
    @Validated(value = {Default.class, WordDto.OnCreated.class})
    public String update(@Valid WordDto word, BindingResult result, Model model) {
        if (result.hasErrors())
            result.failOnError(IllegalArgumentException::new);

        wordService.update(word);

        return "redirect:/words";
    }

    @GetMapping("/words/{id}")
    public String getById(@PathVariable Long id, Model model) {
        final WordEntity entity = wordRepository.safeFindWordById(id);

        model.addAttribute("word", entity);

        return "word_card";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id, Model model) {
        wordRepository.deleteById(id);

        return "redirect:/words";
    }

}
