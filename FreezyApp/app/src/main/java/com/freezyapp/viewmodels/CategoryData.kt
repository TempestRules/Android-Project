package com.freezyapp.viewmodels

import java.util.*

class CategoryData {
    private var accessToken: UUID? = null
    private var categoryId: Long? = null
    private var name: String? = null
    private var color: String? = null

    fun getAccessToken(): UUID? {
        return accessToken
    }

    fun setAccessToken(accessToken: UUID?) {
        this.accessToken = accessToken
    }

    fun getCategoryId(): Long? {
        return categoryId
    }

    fun setCategoryId(categoryId: Long?) {
        this.categoryId = categoryId
    }

    fun getColor(): String? {
        return color
    }

    fun setColor(color: String?) {
        this.color = color
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
}