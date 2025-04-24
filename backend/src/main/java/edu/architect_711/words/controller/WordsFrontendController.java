package edu.architect_711.words.controller;

import edu.architect_711.words.controller.service.LanguageService;
import edu.architect_711.words.controller.service.WordService;
import edu.architect_711.words.entities.db.LanguageEntity;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.entities.mapper.WordMapper;
import edu.architect_711.words.repository.LanguageRepository;
import edu.architect_711.words.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Controller @RequiredArgsConstructor @RequestMapping("/words")
public class WordsFrontendController {
    private final WordService wordService;
    private final WordRepository wordRepository;
    private final LanguageService languageService;

    private final LanguageRepository languageRepository;

    private final static WordMapper wm = new WordMapper();

    @GetMapping
    public String words(
            @RequestParam(defaultValue = "5", name = "size") int size,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "", name = "title") String title,
            @RequestParam(defaultValue = "", name = "language") String lang,
            Model model
    ) {
        wordService.paginatedQueriedFind(model, size, page, title, lang);

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
        wordService.create(parseFormData(map, true));

        return "redirect:/words/new";
    }

    private static WordDto parseFormData(final Map<String, String> map, final boolean ignoreAutos) {
        return new WordDto(
                ignoreAutos ? null : Long.parseLong(map.get("id")),
                map.get("title"),
                map.get("translation"),
                map.get("description"),
                map.get("language"),
                ignoreAutos ? null : LocalDateTime.parse(map.get("localDateTime"))
        );
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
    public String updateOne(@RequestParam Map<String, String> map) {
        final WordDto dto = parseFormData(map, true);
        final WordEntity foundWord = wordRepository.safeFindWordById(Long.valueOf(map.get("id")));
        final LanguageEntity languageEntity = languageRepository.safeFindByTitle(dto.getLanguage());

        foundWord.setTitle(dto.getTitle());
        foundWord.setTranslation(dto.getTranslation());
        foundWord.setDescription(dto.getDescription());
        foundWord.setLanguageEntity(languageEntity);

        wordRepository.save(foundWord);

        return "redirect:/words/" + foundWord.getId();
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
