package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sprite {
    @JsonProperty("front_default")
    private String frontDefault;

    @JsonProperty("back_default")
    private String backDefault;

    public String getBackDefault() {
        return backDefault;
    }

    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }
}
