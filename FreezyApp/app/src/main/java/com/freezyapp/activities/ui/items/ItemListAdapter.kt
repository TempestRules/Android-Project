package com.freezyapp.activities.ui.items

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.utils.widget.MockView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.freezyapp.R
import com.freezyapp.activities.ui.storage.StorageListAdapter
import com.freezyapp.activities.ui.storage.fragements.StorageFormFragment
import com.freezyapp.viewmodels.CategoryModel
import com.freezyapp.viewmodels.ItemModel
import com.freezyapp.viewmodels.StorageModel
import com.freezyapp.viewmodels.entities.Item
import com.freezyapp.viewmodels.entities.Storage_Unit

class ItemListAdapter(private val data: List<Item>, private val storageViewModel: StorageModel, private val categoryModel: CategoryModel, private val itemModel: ItemModel, private val fragmentManager: FragmentManager): RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    // https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val newView = LayoutInflater.from(parent.context).inflate(R.layout.storage_list_item, parent, false)
        return ViewHolder(newView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}