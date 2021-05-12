package com.freezy.freezy_backend.Domain.RequestBodies;

import java.time.LocalDateTime;
import java.util.List;

public class ItemReturnBody {

    private String id;
    private LocalDateTime expirationDate;
    private String unit;
    private Double quantity;
    private List<Long> categoryIds;

    public ItemReturnBody(String id, LocalDateTime expirationDate, String unit, Double quantity) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.unit = unit;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
