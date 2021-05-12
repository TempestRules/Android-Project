package com.freezyapp.activities.ui.items

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.utils.widget.MockView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.freezyapp.R
import com.freezyapp.activities.ui.items.fragements.ItemDialogFragment
import com.freezyapp.activities.ui.items.recyclelistitems.CategoryRecycleListItem
import com.freezyapp.activities.ui.items.recyclelistitems.ItemRecycleListItem
import com.freezyapp.activities.ui.items.recyclelistitems.StorageRecyleListItem
import com.freezyapp.viewmodels.CategoryModel
import com.freezyapp.viewmodels.ItemModel
import com.freezyapp.viewmodels.StorageModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ItemListAdapter(private val data: List<RecycleListItem>, private val storageViewModel: StorageModel, private val categoryModel: CategoryModel, private val itemModel: ItemModel, private val fragManager: FragmentManager): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val layout = view.findViewById<ConstraintLayout>(R.id.item_layout)
        val categoryColor = view.findViewById<MockView>(R.id.item_category_color)
        val title = view.findViewById<TextView>(R.id.item_title_txt)
        val expireDate = view.findViewById<TextView>(R.id.item_expiration_txt)
        val quantity = view.findViewById<TextView>(R.id.item_quantity_txt)
    }

    class CategoryHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val background = view.findViewById<ConstraintLayout>(R.id.category_header_background)
        val title = view.findViewById<TextView>(R.id.category_header_title)
    }

    class StorageHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val background = view.findViewById<ConstraintLayout>(R.id.storage_header_background)
        val title = view.findViewById<TextView>(R.id.storage_header_title)
    }

    override fun getItemViewType(position: Int): Int {
        when {
            data[position] is ItemRecycleListItem -> {
                return 0
            }
            data[position] is CategoryRecycleListItem -> {
                return 1
            }
            data[position] is StorageRecyleListItem -> {
                return 2
            }
        }

        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        when {
            viewType == 0 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_item, parent, false)
                viewHolder = ItemViewHolder(view)
            }
            viewType == 1 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_category_header_item, parent, false)
                viewHolder = CategoryHeaderViewHolder(view)
            }
            viewType == 2 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_storage_header_item, parent, false)
                viewHolder = StorageHeaderViewHolder(view)
            }
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            holder is ItemViewHolder -> {
                val recycleItem = data[position] as ItemRecycleListItem
                val item = recycleItem.getItem()

                holder.categoryColor.setBackgroundColor(Color.parseColor(recycleItem.getCategoryColor()))
                holder.title.text = item.getName()
                holder.quantity.text = item.getQuantity().toString() + " " + item.getUnit()

                if (item.getExpiration_date() != null) {

                    var expireDate: LocalDateTime? = null
                    expireDate = LocalDateTime.parse(item.getExpiration_date(), DateTimeFormatter.ISO_DATE_TIME)

                    val today = LocalDateTime.now()
                    val diff = today!!.until(expireDate, ChronoUnit.DAYS)

                    if (diff < 0) {
                        holder.expireDate.text = "Expired"
                        holder.expireDate.setBackgroundColor(Color.LTGRAY)
                    }
                    else {
                        holder.expireDate.text = diff.toString() + " Days"
                    }
                }
                else {
                    holder.expireDate.text = ""
                }

                holder.view.setOnLongClickListener {
                    itemModel.setCurrentItem(item)
                    fragManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.item_frag_container, ItemDialogFragment(itemModel, fragManager))
                        addToBackStack("dialog")
                    }

                    true
                }
            }
            holder is CategoryHeaderViewHolder -> {
                val recycleItem = data[position] as CategoryRecycleListItem
                val category = recycleItem.getCategory()

                holder.title.text = category.getName()
                holder.title.setBackgroundColor(Color.parseColor(category.getColor()))
            }
            holder is StorageHeaderViewHolder -> {
                val recycleItem = data[position] as StorageRecyleListItem
                val storage = recycleItem.getStorage()

                holder.title.text = storage.getName()
                holder.title.setBackgroundColor(Color.parseColor(storage.getColor()))
            }
        }

    }
}