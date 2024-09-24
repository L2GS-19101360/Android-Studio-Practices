package com.example.pokemonrecycleview;

public class Pokemon {
    private String name;
    private int imageResource;

    public Pokemon(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
