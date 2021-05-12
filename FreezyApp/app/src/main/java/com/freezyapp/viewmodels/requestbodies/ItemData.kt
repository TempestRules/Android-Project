package com.freezyapp.viewmodels.requestbodies

import java.time.LocalDateTime
import java.util.*

class ItemData {
    private var accessToken: UUID? = null
    private var itemId: Long? = null
    private var name: String? = null
    private var expirationDate: String? = null
    private var unit: String? = null
    private var quantity: Double? = null
    private var storage_Unit_Id: Long? = null
    private var categoryIds: List<Long>? = null

    fun getAccessToken(): UUID? {
        return accessToken
    }

    fun setAccessToken(accessToken: UUID?) {
        this.accessToken = accessToken
    }

    fun getQuantity(): Double? {
        return quantity
    }

    fun setQuantity(quantity: Double){
        this.quantity = quantity
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getExpirationDate(): String? {
        return expirationDate
    }

    fun setExpirationDate(expirationDate: String?) {
        this.expirationDate = expirationDate
    }

    fun getUnit(): String? {
        return unit
    }

    fun setUnit(unit: String?) {
        this.unit = unit
    }

    fun getStorage_Unit_Id(): Long? {
        return storage_Unit_Id
    }

    fun setStorage_Unit_Id(storage_Unit_Id: Long?) {
        this.storage_Unit_Id = storage_Unit_Id
    }

    fun getCategoryIds(): List<Long?>? {
        return categoryIds
    }

    fun setCategoryIds(categoryIds: List<Long>?) {
        this.categoryIds = categoryIds
    }

    fun getItemId(): Long? {
        return itemId
    }

    fun setItemId(itemId: Long?) {
        this.itemId = itemId
    }

    override fun toString(): String {
        return "ItemData(accessToken=$accessToken, itemId=$itemId, name=$name, expirationDate=$expirationDate, unit=$unit, quantity=$quantity, storage_Unit_Id=$storage_Unit_Id, categoryIds=$categoryIds)"
    }


}