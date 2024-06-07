package dao;

import models.LanguageCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class LanguageCardDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    LanguageCardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LanguageCard> getLanguageCards() {
        assert jdbcTemplate != null;
        return jdbcTemplate.query("SELECT * FROM public.learnedWordPairs",
                new BeanPropertyRowMapper<>(LanguageCard.class));
    }

    public void addLanguageCard(LanguageCard languageCard) {
        jdbcTemplate.update("INSERT INTO public.learnedWordPairs (word, translation) VALUES (?, ?)",
                languageCard.getWord(), languageCard.getTranslation());
    }

    public void delete(String word) {
        jdbcTemplate.update("DELETE FROM learnedWordPairs WHERE word = ?", word);
    }

    public boolean checkSameWordInBase(String word) {
            String sql = "SELECT COUNT(*) FROM learnedWordPairs WHERE word = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, word);
        return count != null && count > 0;
    }

    public LanguageCard getRandomLanguageCard(List<LanguageCard> languageCards) {
        Random random = new Random();
        if (!languageCards.isEmpty()) {
            return languageCards.get(random.nextInt(languageCards.size()));
        } else {
            return null;
        }
    }

    public String getRandomValue(LanguageCard languageCard) {
        Random random = new Random();
        boolean chooseWord = random.nextBoolean();
        return chooseWord ? languageCard.getWord() : languageCard.getTranslation();
    }

    public boolean isCorrectAnswer(LanguageCard languageCard, String answer, String value) {
        String trueAnswer;
        String valueType = value.equals(languageCard.getWord()) ? "word" : "translation";
        if(valueType.equals("word")){
            trueAnswer = languageCard.getTranslation();
        }else {
            trueAnswer = languageCard.getWord();
        }
        return answer.equals(trueAnswer);
    }
}