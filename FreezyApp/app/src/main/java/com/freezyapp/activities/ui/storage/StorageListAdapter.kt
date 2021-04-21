package com.freezyapp.activities.ui.storage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.utils.widget.MockView
import androidx.recyclerview.widget.RecyclerView
import com.freezyapp.R

class StorageListAdapter(private val data: List<Void>): RecyclerView.Adapter<StorageListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val editBtn: Button = view.findViewById(R.id.storage_item_edit_btn)
        val deleteBtn: Button = view.findViewById(R.id.storage_item_delete_btn)
        val itemTitle: TextView = view.findViewById(R.id.storage_item_title)
        val itemColor: MockView = view.findViewById(R.id.storage_item_color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val newView = LayoutInflater.from(parent.context).inflate(R.layout.storage_list_item, parent, false)
        return ViewHolder(newView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}