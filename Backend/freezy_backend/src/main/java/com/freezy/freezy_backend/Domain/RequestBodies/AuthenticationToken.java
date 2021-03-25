package com.freezy.freezy_backend.Domain.RequestBodies;

import java.util.UUID;

public class AuthenticationToken {

    private UUID authenticationToken;

    public UUID getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(UUID authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}
