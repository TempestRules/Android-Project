package com.freezy.freezy_backend.Domain.RequestBodies;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ItemBody {

    private UUID accessToken;
    private Long itemId;
    private String name;
    private LocalDateTime expirationDate;
    private String unit;
    private Long storage_Unit_Id;
    private List<Long> categoryIds;

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

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getStorage_Unit_Id() {
        return storage_Unit_Id;
    }

    public void setStorage_Unit_Id(Long storage_Unit_Id) {
        this.storage_Unit_Id = storage_Unit_Id;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
