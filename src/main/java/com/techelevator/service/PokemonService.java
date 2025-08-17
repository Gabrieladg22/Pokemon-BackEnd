package com.techelevator.service;


import com.techelevator.model.Pokemon;
import com.techelevator.model.PokemonDetail;
import com.techelevator.model.Results;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

public class PokemonService {
    private RestClient restClient = RestClient.create();
    private final String API_URL = "https://pokeapi.co/api/v2/pokemon";

    public List<Pokemon> getAllPokemon() {
        Results rs = restClient.get()
                .uri(API_URL)
                .retrieve()
                .body(Results.class);

                //restTemplate.getForObject(API_URL, Results.class);
        return rs.getResults();
    }

    public List<Pokemon> getMorePokemon(int startVal){
        Results rs = restClient.get()
                .uri(API_URL + "?offset=" + startVal +
                        "&limit=20")
                .retrieve()
                .body(Results.class);
        List<Pokemon> list = null;
        list = rs.getResults();
        for (Pokemon p : list){
            String url = p.getUrl();
            int pokemonIndex = url.indexOf("pokemon/");
            String pokemonString = url.substring(pokemonIndex);  //"pokemon/index/"
            int slashIndex = pokemonString.indexOf("/");
            String number = pokemonString.substring(slashIndex + 1, pokemonString.length() - 1);
            int id = Integer.parseInt(number);
            p.setId(id);
        }
        return list;
    }

    public PokemonDetail getPokemonDetailById(int id){
        PokemonDetail details = restClient
                .get()
                .uri(API_URL + "/" + id)
                .retrieve()
                .body(PokemonDetail.class);
        return details;
    }


}
