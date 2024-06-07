package controllers;
import dao.LanguageCardDAO;
import dao.UserDAO;
import jakarta.validation.Valid;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/HomePage/WordsTrainer")
public class WordsTrainerController {

private final LanguageCardManager languageCardManager;
private final LanguageCardDAO languageCardDAO;
private final UserDAO userDAO;
private final AuthenticatedUserUtil authenticatedUserUtil;

private List<LanguageCard> learnedLanguageCards;
private List<LanguageCard> languageCardsToLearn;
@Autowired
   public WordsTrainerController(LanguageCardManager languageCardManager, LanguageCardDAO languageCardDAO,
                                 UserDAO userDAO, AuthenticatedUserUtil authenticatedUserUtil){
       this.languageCardManager = languageCardManager;
       this.languageCardDAO = languageCardDAO;
       this.userDAO = userDAO;
    this.authenticatedUserUtil = authenticatedUserUtil;
}
    @GetMapping("/words")
    public String showLanguageCards(Model model) {
        model.addAttribute("languageCards", userDAO.getLearnedLanguageCardsByUser(
                authenticatedUserUtil.getUsername()));
        return "words";
    }
    @PostMapping("/add")
    public String addWordPair(@ModelAttribute("languageCard") @Valid LanguageCard languageCard,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("invalidForm", "Your language card has invalid form. " +
                    "Try to create it again");
            return "redirect:/HomePage/WordsTrainer/words";
        }else if(userDAO.isLanguageCardExists(languageCard, authenticatedUserUtil.getUserId())){
          model.addAttribute("languageCardExists", "This language card is already in base." +
                  "If you want to continue, your language card will be saved in option 'repeated language card'.");
        }else{
languageCardsToLearn.add(languageCard);
        }
        return "wordsTrainer";
    }
@GetMapping
public String showCountLanguageCardsInBase(Model model){
    model.addAttribute(userDAO.getLearnedWordsByUserId(authenticatedUserUtil.getUserId()).size());
    return "wordsTrainer";
}
@PostMapping
public String setCountCorrectAnswers(@RequestParam("correctAnswers") int correctAnswers){
    languageCardManager.setCountCorrectAnswersForFinish(correctAnswers);
return "settingCorrectAnswers";
}
    @PostMapping("/check-answer")
    public String checkAnswer(@RequestParam("answer") String answer, Model model) {
       String value = (String)model.getAttribute("randomValue");
       LanguageCard languageCard = (LanguageCard) model.getAttribute("randomLanguageCard");
        assert languageCard != null;
        assert value != null;
        boolean isCorrectAnswer = languageCardDAO.isCorrectAnswer(languageCard, answer, value);
        languageCard.checkAnswer(isCorrectAnswer);
        if(!isCorrectAnswer){
            languageCard.incrementMistakesCount(1);
        }
        model.addAttribute("mistakesCount", languageCard.getMistakesCount());
        model.addAttribute("isCorrectAnswer", isCorrectAnswer);
      model.addAttribute("countCorrectAnswers", languageCard.getCountOfCorrectAnswers());
      model.addAttribute("countCorrectAnswersToFinish",languageCardManager.getCountCorrectAnswersForFinish());
      if(languageCard.getCountOfCorrectAnswers() == languageCardManager.getCountCorrectAnswersForFinish()){
          learnedLanguageCards.add(languageCard);
          languageCardsToLearn.remove(languageCard);
          model.addAttribute("learnedWord", "You learned this word");
          if(languageCardsToLearn.isEmpty()){
              User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
             userDAO.addLanguageCardsInBase(learnedLanguageCards, authenticatedUser.getUsername());
              model.addAttribute("finish", "You learned all words");
              return "redirect:/HomePage/WordTrainer/finish";
          }else{
              languageCard = languageCardDAO.getRandomLanguageCard(languageCardsToLearn);
              value = languageCardDAO.getRandomValue(languageCard);
              model.addAttribute("randomLanguageCard", languageCard);
              model.addAttribute("randomValue", value);
          }
      }
        return "checkAnswer";
    }
    @GetMapping
    public String showWordsTrainerPage(Model model){
       LanguageCard randomLanguageCard = languageCardDAO.getRandomLanguageCard(languageCardsToLearn);
String randomValue = languageCardDAO.getRandomValue(randomLanguageCard);
if(randomLanguageCard == null || randomValue == null){
    model.addAttribute("errorPage", "Something gone wrong");
    return "errorPage";
}else{
    model.addAttribute("randomValue", randomValue);
    model.addAttribute("randomLanguageCard", randomLanguageCard);
    return "wordsTrainer";
}
   }
}