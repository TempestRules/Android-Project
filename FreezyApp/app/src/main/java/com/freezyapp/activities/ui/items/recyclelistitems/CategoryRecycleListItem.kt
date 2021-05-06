package com.freezyapp.activities.ui.items.recyclelistitems

import com.freezyapp.activities.ui.items.RecycleListItem
import com.freezyapp.viewmodels.entities.Category

class CategoryRecycleListItem(private val category: Category) : RecycleListItem {

    fun getCategory(): Category {
        return category
    }
}