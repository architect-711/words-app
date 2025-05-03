package edu.architect_711.words.controller;

import edu.architect_711.words.controller.service.LanguageService;
import edu.architect_711.words.controller.service.WordService;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.entities.mapper.WordMapper;
import edu.architect_711.words.repository.LanguageRepository;
import edu.architect_711.words.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller @RequiredArgsConstructor @RequestMapping("/words")
public class WordsFrontendController {
    private final WordService wordService;
    private final WordRepository wordRepository;
    private final LanguageService languageService;

    private final LanguageRepository languageRepository;

    private final static WordMapper wm = new WordMapper();

    @GetMapping
    public String words(
            @RequestParam(defaultValue = "10", name = "size") int size,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "", name = "title") String title,
            @RequestParam(defaultValue = "", name = "lang") String lang,
            Model model
    ) {
        ResponseEntity<List<WordDto>> listResponseEntity = wordService.find(size, page, title, lang);

        model.addAttribute("size", size);
        model.addAttribute("page", page);
        model.addAttribute("title", title);
        model.addAttribute("lang", lang);
        model.addAttribute("langs", languageService.findAll().getBody());
        model.addAttribute("words", listResponseEntity.getBody());

        return "words";
    }



    /* ------------------------------------------------- */
    /*                      CREATION                     */
    /* ------------------------------------------------- */
    @GetMapping("/new")
    public String newWord(Model model) {
        model.addAttribute("word", new WordDto());
        model.addAttribute("langs", languageService.findAll().getBody());

        return "new_word";
    }
    @PostMapping("/new")
    public String saveNew(@ModelAttribute("wordForm") WordDto dto, Model model) {
        wordService.create(dto);

        return "redirect:/words/new";
    }



    /* ------------------------------------------------- */
    /*                      WORD CARD                    */
    /* ------------------------------------------------- */
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        final WordEntity entity = wordRepository.safeFindWordById(id);

        model.addAttribute("word", wm.toDto(entity));
        model.addAttribute("langs", languageService.findAll().getBody());

        return "word_card";
    }



    /* ------------------------------------------------- */
    /*                      UPDATE                       */
    /* ------------------------------------------------- */
    @PostMapping("/update")
    public String updateOne(@ModelAttribute("wordForm") WordDto dto) {
        WordDto response = wordService.update(dto).getBody();

        return "redirect:/words/" + response.getId();
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
