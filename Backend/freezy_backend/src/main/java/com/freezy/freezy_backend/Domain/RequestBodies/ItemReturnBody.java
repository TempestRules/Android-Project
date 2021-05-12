package com.freezy.freezy_backend.Domain.RequestBodies;

import java.time.LocalDateTime;
import java.util.List;

public class ItemReturnBody {

    private String id;
    private String expirationDate;
    private String unit;
    private Double quantity;
    private Long storage_unit_id;
    private List<Long> categoryIds;

    public ItemReturnBody(String id, String expirationDate, String unit, Double quantity, Long storage_unit_id) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.unit = unit;
        this.quantity = quantity;
        this.storage_unit_id = storage_unit_id;
    }

    public Long getStorage_unit_id() {
        return storage_unit_id;
    }

    public void setStorage_unit_id(Long storage_unit_id) {
        this.storage_unit_id = storage_unit_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
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
