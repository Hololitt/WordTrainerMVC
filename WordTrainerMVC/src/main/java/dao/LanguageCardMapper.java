package dao;

import models.LanguageCard;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageCardMapper implements RowMapper<LanguageCard> {

    @Override
    public LanguageCard mapRow(ResultSet resultSet, int i) throws SQLException {
        LanguageCard languageCard = new LanguageCard();

      languageCard.setWord(resultSet.getString("word"));
      languageCard.setTranslation(resultSet.getString("translation"));
        return languageCard;
    }
}
