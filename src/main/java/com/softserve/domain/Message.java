
package com.softserve.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    String message;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }
}
