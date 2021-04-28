package com.freezyapp.viewmodels.entities

class Storage_Unit {
    private var id: Long = 0
    private lateinit var name: String
    private lateinit var color: String

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

    fun getColor(): String{
        return color
    }

    fun setColor(color: String){
        this.color = color
    }

    override fun toString(): String {
        return "Storage_Unit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}'
    }

    override fun equals(other: Any?): Boolean {
        if(other is Storage_Unit) return id == other.getId()
        return false
    }
}