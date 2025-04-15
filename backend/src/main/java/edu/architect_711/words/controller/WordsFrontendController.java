package edu.architect_711.words.controller;

import edu.architect_711.words.controller.service.LanguageService;
import edu.architect_711.words.controller.service.WordService;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.repository.WordRepository;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller @RequiredArgsConstructor @RequestMapping("/words")
@Validated
public class WordsFrontendController {
    private final WordService wordService;
    private final WordRepository wordRepository;
    private final LanguageService languageService;

    @GetMapping
    public String words() {
        return "words";
    }



    /* ------------------------------------------------- */
    /*                  FIND WORDS                       */
    /* ------------------------------------------------- */
    @GetMapping("/find")
    public String find(
            @RequestParam(defaultValue = "5", name = "size") int size,
            @RequestParam(defaultValue = "0", name = "page") int page,
            Model model
    ) {
        model.addAttribute("words", wordService.read(size, page).getBody());
        model.addAttribute("langs", languageService.findAll().getBody());
        model.addAttribute("size", size);
        model.addAttribute("page", page);

        return "find_words";
    }

    @PostMapping(path = "/find", consumes = {"application/x-www-form-urlencoded"})
    public String findByTitle(
            @RequestParam Map<String, String> map,
            Model model
    ) {
        List<WordDto> foundWords;

        String title = map.get("title");
        String lang = map.get("lang");

        if (!title.isBlank())
            foundWords = wordService.findByTitle(title).getBody();
        else if (!lang.isBlank())
            foundWords = wordService.findByLang(lang).getBody();
        else
            foundWords = List.of();

        model.addAttribute("words", foundWords);
        model.addAttribute("langs", languageService.findAll().getBody());
        model.addAttribute("size", 5);
        model.addAttribute("page", 0);

        return "find_words";
    }



    /* ------------------------------------------------- */
    /*                  CREATE WORDS                     */
    /* ------------------------------------------------- */
    @GetMapping("/new")
    public String newOne(Model model) {
        model.addAttribute("word", new WordDto());
        model.addAttribute("langs", languageService.findAll().getBody());

        return "new_word";
    }

    @PostMapping(path = "/new", consumes = {"application/x-www-form-urlencoded"})
    public String saveNew(@RequestParam Map<String, String> map) {
        wordService.create(new WordDto(
                null,
                map.get("title"),
                map.get("translation"),
                map.get("description"),
                map.get("language"),
                null
        ));

        return "redirect:/words";
    }




    /* ------------------------------------------------- */
    /*                   UPDATE WORDS                    */
    /* ------------------------------------------------- */
    @PostMapping("/update")
    @Validated(value = {Default.class, WordDto.OnCreated.class})
    public String update(@Valid WordDto word, BindingResult result, Model model) {
        if (result.hasErrors())
            result.failOnError(RuntimeException::new);

        wordService.update(word);

        return "redirect:/words";
    }




    /* ------------------------------------------------- */
    /*                      WORD CARD                    */
    /* ------------------------------------------------- */
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        final WordEntity entity = wordRepository.safeFindWordById(id);

        model.addAttribute("word", entity);
        model.addAttribute("langs", languageService.findAll().getBody());

        return "word_card";
    }




    /* ------------------------------------------------- */
    /*                   DELETE WORDS                    */
    /* ------------------------------------------------- */
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id, Model model) {
        wordRepository.deleteById(id);

        return "redirect:/words";
    }
}
