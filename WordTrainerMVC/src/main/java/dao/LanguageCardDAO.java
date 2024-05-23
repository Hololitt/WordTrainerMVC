package dao;

import models.LanguageCard;
import models.LanguageCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
public class LanguageCardDAO {
    private final JdbcTemplate jdbcTemplate;
private final List<LanguageCard> languageCards = index();
    @Autowired
    LanguageCardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LanguageCard> index() {
        assert jdbcTemplate != null;
        return jdbcTemplate.query("SELECT * FROM public.WordPairs",
                new BeanPropertyRowMapper<>(LanguageCard.class));
    }

    public void add(LanguageCardRepository languageCardRepository) {
        jdbcTemplate.update("INSERT INTO public.WordPairs (word, translation) VALUES (?, ?)",
                languageCardRepository.getWord(), languageCardRepository.getTranslation());
    }

    public void delete(String word) {
        jdbcTemplate.update("DELETE FROM WordPairs WHERE word = ?", word);
    }

    public boolean check(String word) {
        String sql = "SELECT COUNT(*) FROM WordPairs WHERE word = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, word);
        return count != null && count > 0;
    }

    public LanguageCard getRandomLanguageCard(){
        Random random = new Random();
        if(!languageCards.isEmpty()){
            return languageCards.get(random.nextInt(languageCards.size()));
        }else{
            return null;
        }
    }
public String getRandomValue(LanguageCard languageCard){
        Random random = new Random();
    boolean chooseWord = random.nextBoolean();
    return chooseWord ? languageCard.getWord() : languageCard.getTranslation();
    }
    public String start(LanguageCard languageCard, String answer, int incrementCount) {
        String randomValue = getRandomValue(getRandomLanguageCard());
        boolean isCorrect = Objects.equals(answer, languageCard.getTranslation());
        languageCard.checkAnswer(isCorrect);
        if(languageCard.getCountOfCorrectAnswers() == incrementCount){
            languageCards.remove(languageCard);
        }
        if (isCorrect) {
            return randomValue.equals(languageCard.getWord()) ? languageCard.getTranslation() : languageCard.getWord();
        }
        return null;
    }
}