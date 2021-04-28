package com.freezyapp.viewmodels.entities

import java.time.LocalDateTime

class Item {
    private var id: Long = 0
    private lateinit var unit: String
    private var quantity: Double = 0.0
    private var expiration_date: LocalDateTime? = null
    private lateinit var name: String
    private lateinit var categoryIds: List<Long>
    private var storage_unit_id: Long = 0

    fun Item(name: String, expiration_date: LocalDateTime, quantity: Double, unit: String) {
        this.name = name
        this.expiration_date = expiration_date
        this.unit = unit
        this.quantity = quantity
    }

    //Getters and setters
    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setCategory(categoryIds: List<Long>){
        this.categoryIds = categoryIds
    }

    fun getCategoryIds(): List<Long> {
        return categoryIds
    }

    fun setStorageUnitId(storage_unit_id: Long){
        this.storage_unit_id = storage_unit_id
    }

    fun getStorageUnitId(): Long {
        return storage_unit_id
    }

    fun setQuantity(quantity: Double){
        this.quantity = quantity
    }

    fun getQuantity(): Double {
        return quantity
    }

    fun getExpiration_date(): LocalDateTime? {
        return expiration_date
    }

    fun setExpiration_date(expiration_date: LocalDateTime?) {
        this.expiration_date = expiration_date
    }

    fun getUnit(): String? {
        return unit
    }

    fun setUnit(unit: String) {
        this.unit = unit
    }

    fun getId(): Long? {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }
    override fun toString(): String {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", expiration_date=" + expiration_date +
                ", unit='" + unit + '\'' +
                ", categoryIds" + categoryIds + '\'' +
                ", Storage_Unit_Id" + storage_unit_id + '\'' +
                '}'
    }
}