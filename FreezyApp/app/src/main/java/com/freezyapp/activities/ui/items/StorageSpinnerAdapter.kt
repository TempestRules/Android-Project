package com.freezyapp.activities.ui.items

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.freezyapp.viewmodels.entities.Storage_Unit

class StorageSpinnerAdapter(val spinnerContext: Context, val values: List<Storage_Unit>) : ArrayAdapter<Storage_Unit>(spinnerContext, android.R.layout.simple_spinner_item, values) {
    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): Storage_Unit? {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView: TextView = super.getView(position, convertView, parent) as TextView
        textView.setTextColor(Color.BLACK)
        textView.text = values[position].getName()
        return textView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView: TextView = super.getDropDownView(position, convertView, parent) as TextView
        textView.setTextColor(Color.BLACK)
        textView.text = values[position].getName()
        return textView
    }
}