package edu.architect_711.words.controller;

import edu.architect_711.words.controller.service.LanguageService;
import edu.architect_711.words.controller.service.WordService;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller @RequiredArgsConstructor @RequestMapping("/words")
public class WordsFrontendController {
    private final WordService wordService;
    private final WordRepository wordRepository;
    private final LanguageService languageService;

    @GetMapping
    public String words() {
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
    public String saveNew(@RequestParam Map<String, String> map, Model model) {
        // fucking shit code, I am tired, so won't do better. legacy uuhooo!
        wordService.create(new WordDto(
                null,
                map.get("title"),
                map.get("translation"),
                map.get("description"),
                map.get("language"),
                null
        ));

        return "redirect:/words/find";
    }


    /* ------------------------------------------------- */
    /*                  FIND WORDS                       */
    /* ------------------------------------------------- */
    @GetMapping("/find")
    public String find(
            @RequestParam(defaultValue = "5", name = "size") int size,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "", name = "title") String title,
            @RequestParam(defaultValue = "", name = "lang") String lang,
            Model model
    ) {
        wordService.paginatedQueriedFind(model, size, page, title, lang);

        return "find_words";
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
