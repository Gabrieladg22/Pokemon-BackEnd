package com.techelevator.dao;

import com.techelevator.model.PokemonDetail;
import com.techelevator.model.Sprite;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcPokemonDao implements PokemonDao{

    private JdbcTemplate template;

    public JdbcPokemonDao(DataSource datasource) {
        this.template = new JdbcTemplate(datasource);
    }

    @Override
    public PokemonDetail saveFavorites(PokemonDetail detail, int userId) {
        String sql = "INSERT INTO pokemon (api_id, name, base_experience, height, weight, back_url, front_url) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING id";
        int id = template.queryForObject(sql, int.class, detail.getApiId(), detail.getName(),
        detail.getBaseExperience(), detail.getHeight(), detail.getWeight(), detail.getSprite().getBackDefault(),
                detail.getSprite().getFrontDefault());

        //Insert into the users_pokemon table
        sql = "INSERT INTO users_pokemon (pokemon_id, users_id) VALUES (?, ?)";
        template.update(sql, id, userId);

        //Error checking to make sure it was written to the database
        sql = "SELECT * FROM pokemon WHERE id = ?";
        SqlRowSet results = template.queryForRowSet(sql, id);
        if(results.next()){
            return mapRowToPokemonDetail(results);
        }
        return null;
    }

    @Override
    public List<PokemonDetail> getAllFavorites(int userId) {
        String sql = "SELECT p.id, api_id, name, base_experience, height, weight," +
                " back_url, front_url FROM pokemon p" +
                " JOIN users_pokemon up ON p.id = up.pokemon_id WHERE users_id = ?";
        SqlRowSet results = template.queryForRowSet(sql, userId);
        List<PokemonDetail> list = new ArrayList<>();
        while (results.next()){
            list.add(mapRowToPokemonDetail(results));
        }
        return list;
    }

    private PokemonDetail mapRowToPokemonDetail(SqlRowSet rs){
        PokemonDetail detail = new PokemonDetail();
        detail.setId(rs.getInt("id"));
        detail.setApiId(rs.getInt("api_id"));
        detail.setName(rs.getString("name"));
        detail.setBaseExperience(rs.getInt("base_experience"));
        detail.setHeight(rs.getInt("height"));
        detail.setWeight(rs.getInt("weight"));
        Sprite sprite = new Sprite();
        sprite.setFrontDefault(rs.getString("front_url"));
        sprite.setBackDefault(rs.getString("back_url"));
        detail.setSprite(sprite);
        return detail;
    }
}
