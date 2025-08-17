package com.techelevator.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class DadJokeService {

    private final String API_URL="https://icanhazdadjoke.com";

    private RestClient restClient = RestClient.create();

    public String getDadJoke() {

        String response = restClient
                .get()
                .uri(API_URL)
                .header(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN_VALUE)
                .retrieve()
                .body(String.class);
        return response;
    }
    
}
