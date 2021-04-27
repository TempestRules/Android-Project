package com.freezyapp.viewmodels.entities

class Category {
    private var id: Long = 0
    private lateinit var color: String
    private lateinit var name: String
    private var currentCategory: Category? = null

    fun Category() {}

    fun Category(name: String, color: String) {
        this.name = name
        this.color = color
    }

    fun getCurrentCategory(): Category?{
        return currentCategory
    }

    fun setCurrentCategory(category: Category){
        currentCategory = category
    }

    //Getters and setters
    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getColor(): String? {
        return color
    }

    fun setColor(color: String) {
        this.color = color
    }

    fun getId(): Long? {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    override fun toString(): String {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}'
    }
}