package com.freezy.freezy_backend.Domain.RequestBodies;

import java.util.UUID;

public class AccountDetails {

    private String name;
    private UUID token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
