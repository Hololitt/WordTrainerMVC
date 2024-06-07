package models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
@Entity
@Table(name = "learnedWordPairs")
public class LanguageCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "word")
    @NotEmpty(message = "Write the word")
    private String word;
    @Column(name = "translation")
    @NotEmpty(message = "Write the translation")
    private String translation;

    private int countOfCorrectAnswers;
    @Column(name = "mistakeCount")
private int mistakesCount;
@Column(name = "repeatCount")
    private int repeatCount;
    public LanguageCard() {}
public int getId(){
        return id;
}
    public String getWord() {
        return word;
    }
    public int getMistakesCount(){
        return mistakesCount;
    }
    public int getRepeatCount(){
        return repeatCount;
    }
    public String getTranslation() {
        return translation;
    }
    public int getCountOfCorrectAnswers(){
        return countOfCorrectAnswers;
    }

public void incrementMistakesCount(int mistakesCount){
        this.mistakesCount += mistakesCount;
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
