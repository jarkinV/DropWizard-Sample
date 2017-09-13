package com.softserve.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private long id;
    private String text;
    private String state;

    public Item() {
    }

    public Item(long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}

    @JsonIgnore
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getText() {
        return text;
    }
    
    @JsonProperty
	public String getState() {
		return state;
	}
}
