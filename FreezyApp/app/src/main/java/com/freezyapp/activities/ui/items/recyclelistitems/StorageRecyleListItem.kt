package com.freezyapp.activities.ui.items.recyclelistitems

import com.freezyapp.activities.ui.items.RecycleListItem
import com.freezyapp.viewmodels.entities.Storage_Unit

class StorageRecyleListItem(private val storage: Storage_Unit) : RecycleListItem {
    fun getStorage() : Storage_Unit {
        return storage
    }
}