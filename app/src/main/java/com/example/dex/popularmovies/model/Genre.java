package com.example.dex.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    /**
     * @param id
     * @param name
     */
    public Genre(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return The unique id for this Genre
     */
    public final long getId() {
        return id;
    }

    /**
     * @return The name for this Genre
     */
    public final String getName() {
        return name;
    }


}
