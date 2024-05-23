package controllers;
import dao.LanguageCardDAO;
import jakarta.validation.Valid;
import models.LanguageCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import models.LanguageCardRepository;
@Controller
@RequestMapping("/HomePage")
public class WordsController {
    private LanguageCardRepository languageCardRepository;
    @Autowired
    private LanguageCardDAO languageCardDAO;

    @GetMapping("/words")
    public String show(Model model) {
        model.addAttribute("languageCards", languageCardDAO.index());
        return "words";
    }
    @PostMapping("/add")
    public String addWordPair(@ModelAttribute("languageCard") @Valid LanguageCardRepository languageCardRepository,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/HomePage/words";
        }else{
            languageCardDAO.add(languageCardRepository);
            model.addAttribute("CorrectAnswer", "Correct!");
        }
        return "redirect:/HomePage/words";
    }

    @PostMapping("/check-answer")
    public String checkAnswer(@ModelAttribute("languageCard") LanguageCard languageCard,
                              @RequestParam("answer") String answer,
                              Model model) {
        String translation = languageCardDAO.start(languageCard, answer, languageCard.getCountOfCorrectAnswers());
        if (translation != null) {
            model.addAttribute("translation", translation);
        }
        return "resultPage";
    }
}