package dao;

import jakarta.validation.Valid;
import models.LanguageCard;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

@Component
public class UserDAO {
    @Autowired
    UserDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
@Autowired
   private PasswordEncoder passwordEncoder;
private final JdbcTemplate jdbcTemplate;
    public void addUser(@Valid User user){
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        jdbcTemplate.update("INSERT INTO Users(username, password, email) VALUES(?, ?, ?)",
                user.getUsername(), hashedPassword, user.getEmail());
    }
    public List<LanguageCard> getMostDifficultLanguageCardsByUser(String username){
    return getLanguageCardsByUser(username, SortingType.MOST_DIFFICULT);
    }
    public List<LanguageCard> getLessRepetitiveLanguageCardsByUser(String username){
    return getLanguageCardsByUser(username, SortingType.LESS_REPETITIVE);
    }
    public List<LanguageCard> getLearnedLanguageCardsByUser(String username){
        return getLanguageCardsByUser(username, SortingType.ALL);
    }
    public enum SortingType {
        MOST_DIFFICULT,
        LESS_REPETITIVE,
        ALL
    }

    private List<LanguageCard> getLanguageCardsByUser(String username, SortingType sortingType) {
        String sql = "SELECT * FROM public.learnedLanguageCards WHERE username = ?";
        List<LanguageCard> languageCards = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(LanguageCard.class), username);

        Comparator<LanguageCard> comparator;
        switch (sortingType) {
            case MOST_DIFFICULT-> comparator = Comparator.comparingInt(LanguageCard::getMistakesCount);
            case LESS_REPETITIVE-> comparator = Comparator.comparingInt(LanguageCard::getRepeatCount).reversed();
            case ALL -> {
                return languageCards;
            }
            default-> throw new IllegalArgumentException("Invalid sorting type: " + sortingType);
        }
        languageCards.sort(comparator);
        return languageCards;
    }
    public User getUserByUsername(String username){
        String sql = "SELECT * FROM public.Users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
    }
    public Integer getUserIdByUsername(String username){
        String sql = "SELECT id FROM public.Users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }
    public boolean isUsernameExists(String username) {
        String sql = "SELECT * FROM public.Users WHERE username = ?";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, username));
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT * FROM public.Users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), email);
        return users.isEmpty();
    }
    public List<LanguageCard> getLearnedWordsByUserId(Integer userId){
        String sql = "SELECT * FROM public.learnedWordPairs WHERE userId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LanguageCard.class), userId);
    }
    public void addLanguageCardsInBase(List<LanguageCard> languageCards, String username){
        String sqlForId = "SELECT id FROM Users WHERE username = ?";
        Integer userId = jdbcTemplate.queryForObject(sqlForId, Integer.class, username);
        if(userId != null){
            String sql = "INSERT INTO learnedWordPairs (userId, word, translation, mistakesCount) VALUES(?, ?, ?, ?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    LanguageCard languageCard = languageCards.get(i);
ps.setInt(1, userId);
ps.setString(2, languageCard.getWord());
ps.setString(3, languageCard.getTranslation());
ps.setInt(4, languageCard.getMistakesCount());
                }
                @Override
                public int getBatchSize() {
                    return languageCards.size();
                }
            });
        }
    }
public boolean isLanguageCardExists(LanguageCard languageCard, int userId){
    String sql = "SELECT COUNT(*) FROM learnedWordPairs WHERE word = ? AND translation = ? AND userId = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, languageCard.getWord(),
                languageCard.getTranslation(), userId);
    return count!=null;
}
}
