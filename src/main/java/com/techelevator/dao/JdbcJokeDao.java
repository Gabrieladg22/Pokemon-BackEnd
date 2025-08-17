package com.techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcJokeDao implements JokeDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcJokeDao(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveJoke(int userId, String text) {
        String sql = "INSERT INTO joke (user_id, joke_text)" +
                     " VALUES(?,?)";
        jdbcTemplate.update(sql, userId, text);
    }

    @Override
    public List<String> returnAllFavoriteJokes(int userId) {
        String sql = "SELECT joke_text FROM joke " +
                "WHERE user_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        List<String> jokeList = new ArrayList<>();

        while(results.next()){
            String joke = results.getString("joke_text");
            jokeList.add(joke);
        }
        return jokeList;
    }
}
