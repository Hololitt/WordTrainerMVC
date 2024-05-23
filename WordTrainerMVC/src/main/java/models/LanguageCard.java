package models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class LanguageCard {
    @NotEmpty(message = "Write the word")
    private String word;
    @NotEmpty(message = "Write the translation")
    private String translation;

    @Min(value=1, message = "Lowest value is 1")
    private int countOfCorrectAnswers;

    public LanguageCard() {}

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
    public int getCountOfCorrectAnswers(){
        return countOfCorrectAnswers;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void checkAnswer(boolean answer) {
        if (answer) {
            countOfCorrectAnswers++;
        } else {
            countOfCorrectAnswers--;
        }
    }
}
