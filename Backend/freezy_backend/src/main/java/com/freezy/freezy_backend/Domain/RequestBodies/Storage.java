package com.freezy.freezy_backend.Domain.RequestBodies;

import java.util.UUID;

public class Storage {

    private UUID accessToken;
    private String name;

    public UUID getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(UUID accessToken) {
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
