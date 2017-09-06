package com.softserve.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private long id;
    private String text;

    public Item() {
    }

    public Item(long id, String text) {
        this.id = id;
        this.text = text;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getText() {
        return text;
    }
}
