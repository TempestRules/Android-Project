package com.freezyapp.viewmodels.requestbodies

import com.freezyapp.viewmodels.entities.Storage_Unit
import java.util.*

class StorageData {
    private var accessToken: UUID? = null
    private var storageId: Long? = null
    private var name: String? = null
    private var color: String? = null

    fun getAccessToken(): UUID? {
        return accessToken
    }

    fun setAccessToken(accessToken: UUID?) {
        this.accessToken = accessToken
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getStorageId(): Long? {
        return storageId
    }

    fun setStorageId(storageId: Long?) {
        this.storageId = storageId
    }

    fun getColor(): String{
        return color!!
    }

    fun setColor(color: String){
        this.color = color
    }
}