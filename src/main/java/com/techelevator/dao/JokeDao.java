package com.techelevator.dao;

import java.util.List;

public interface JokeDao {

    void saveJoke(int userId, String text);

    List<String> returnAllFavoriteJokes(int userId);

}
