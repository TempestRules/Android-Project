package com.freezyapp.viewmodels.entities

class ItemModelBools {
    private var sortCategory: Boolean = false
    private var onlyExpired: Boolean = false
    private var sortStorage: Boolean = false

    fun setSortCategory(sortCategory: Boolean){
        this.sortCategory = sortCategory
    }

    fun getSortCategory(): Boolean {
        return sortCategory
    }

    fun setOnlyExpired(onlyExpired: Boolean) {
        this.onlyExpired = onlyExpired
    }

    fun getOnlyExpired(): Boolean {
        return onlyExpired
    }

    fun setSortStorage(sortStorage: Boolean) {
        this.sortStorage = sortStorage
    }

    fun getSortStorage(): Boolean {
        return sortStorage
    }
}