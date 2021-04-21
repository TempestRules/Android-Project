package com.freezy.freezy_backend.Domain.RequestBodies;

import java.util.UUID;

public class Storage {

    private UUID accessToken;
    private Long storageId;
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

    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }
}
