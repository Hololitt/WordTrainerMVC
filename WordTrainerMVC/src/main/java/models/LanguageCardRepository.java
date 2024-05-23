package models;

import jakarta.validation.constraints.NotEmpty;

public class LanguageCardRepository {
    @NotEmpty(message = "This field is empty")
    private String word;
    @NotEmpty(message = "This field is empty")
    private String translation;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
