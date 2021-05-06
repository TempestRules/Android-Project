package com.freezyapp.activities.ui.items.recyclelistitems

import com.freezyapp.activities.ui.items.RecycleListItem
import com.freezyapp.viewmodels.entities.Item

class ItemRecycleListItem(private val item: Item, private val categoryColor: String) : RecycleListItem {

    fun getItem(): Item {
        return item
    }

    fun getCategoryColor(): String {
        return categoryColor
    }
}