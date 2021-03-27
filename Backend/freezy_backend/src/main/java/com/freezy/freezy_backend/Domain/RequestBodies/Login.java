package com.freezy.freezy_backend.Domain.RequestBodies;

public class Login {

    private String username;
    private String password;
    private String accountDetailsName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountDetailsName() {
        return accountDetailsName;
    }

    public void setAccountDetailsName(String accountDetailsName) {
        this.accountDetailsName = accountDetailsName;
    }
}
