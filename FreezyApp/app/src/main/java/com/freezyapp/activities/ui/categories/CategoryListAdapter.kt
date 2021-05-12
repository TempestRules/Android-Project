package com.freezyapp.activities.ui.categories

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.utils.widget.MockView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.freezyapp.R
import com.freezyapp.activities.ui.categories.fragements.CategoryFormFragment
import com.freezyapp.viewmodels.CategoryModel
import com.freezyapp.viewmodels.ItemModel
import com.freezyapp.viewmodels.entities.Category
import com.freezyapp.viewmodels.entities.Item
import com.freezyapp.viewmodels.entities.Storage_Unit

class CategoryListAdapter(private val data: List<Category>, private val context: Context, private val lifeCycleOwner: LifecycleOwner, private val categoryViewModel: CategoryModel, private val itemViewModel: ItemModel, private val fragmentManager: FragmentManager): RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val editBtn: ImageButton = view.findViewById(R.id.category_item_edit_btn)
        val deleteBtn: ImageButton = view.findViewById(R.id.category_item_delete_btn)
        val itemTitle: TextView = view.findViewById(R.id.category_item_title)
        val itemColor: MockView = view.findViewById(R.id.category_item_color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val newView = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false)
        return ViewHolder(newView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = data[position].getName()
        holder.itemColor.setBackgroundColor(Color.parseColor(data[position].getColor()))
        holder.editBtn.setOnClickListener {
            categoryViewModel.setCurrentCategory(data[position])
            fragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.category_frag_container, CategoryFormFragment())
                addToBackStack(null)
            }
        }
        holder.deleteBtn.setOnClickListener {
            val observer: Observer<List<Item>> = object: Observer<List<Item>> {
                override fun onChanged(t: List<Item>?) {
                    val itemWithCategory = t!!.filter { it.getCategoryIds()[0] == data[position].getId() }
                    if (itemWithCategory.isEmpty()) {
                        categoryViewModel.deleteCategory(data[position].getId()!!)
                    }
                    else {
                        AlertDialog.Builder(context)
                                .setTitle("Delete '" + data[position].getName() + "'?")
                                .setMessage("are you sure you want to delete the category and all items with it?")
                                .setPositiveButton("Confirm", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                                    categoryViewModel.deleteCategory(data[position].getId()!!)
                                })
                                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                                    dialogInterface.dismiss()
                                })
                                .show()
                    }

                    itemViewModel.liveList.removeObserver(this)
                }

            }

            itemViewModel.liveList.observe(lifeCycleOwner, observer)
        }
    }
}