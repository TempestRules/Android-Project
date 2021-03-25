package com.freezy.freezy_backend.Domain.RequestBodies;

public class AccountDetails {

    private String name;
    //TODO: Is collectiontoken a uuid?
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
