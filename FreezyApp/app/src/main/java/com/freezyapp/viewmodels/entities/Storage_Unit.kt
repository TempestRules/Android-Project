package com.freezyapp.viewmodels.entities

import android.R.attr.name
import android.R.id


class Storage_Unit {
    private var id: Long = 0
    private lateinit var name: String

    fun Storage_Unit() {}

    fun Storage_Unit(name: String) {
        this.name = name
    }

    //Getters and setters
    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getId(): Long? {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    override fun toString(): String? {
        return "Storage_Unit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}'
    }
}