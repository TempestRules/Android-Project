package com.freezy.freezy_backend.Domain.RequestBodies;

import java.util.List;

public class ItemReturnBody {

    private Long id;
    private String name;
    private String expirationDate;
    private String unit;
    private Double quantity;
    private Long storage_unit_id;
    private List<Long> categoryIds;

    public ItemReturnBody(Long id, String name, String expirationDate, String unit, Double quantity, Long storage_unit_id) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
        this.unit = unit;
        this.quantity = quantity;
        this.storage_unit_id = storage_unit_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Long getStorage_unit_id() {
        return storage_unit_id;
    }

    public void setStorage_unit_id(Long storage_unit_id) {
        this.storage_unit_id = storage_unit_id;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
}