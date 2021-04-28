package com.freezyapp.viewmodels.entities

import java.time.LocalDateTime

class Item {
    private var id: Long = 0
    private lateinit var unit: String
    private var quantity: Double = 0.0
    private lateinit var expiration_date: LocalDateTime
    private lateinit var name: String

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

    fun setQuantity(quantity: Double){
        this.quantity = quantity
    }

    fun getQuantity(): Double {
        return quantity
    }

    fun getExpiration_date(): LocalDateTime? {
        return expiration_date
    }

    fun setExpiration_date(expiration_date: LocalDateTime) {
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
                '}'
    }
}