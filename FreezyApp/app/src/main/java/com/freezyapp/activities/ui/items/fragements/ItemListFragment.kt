package com.freezyapp.activities.ui.storage.fragements

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freezyapp.R
import com.freezyapp.activities.ui.items.ItemListAdapter
import com.freezyapp.activities.ui.items.fragements.ItemSortDialog
import com.freezyapp.activities.ui.storage.StorageListAdapter
import com.freezyapp.viewmodels.CategoryModel
import com.freezyapp.viewmodels.ItemModel
import com.freezyapp.viewmodels.StorageModel
import com.freezyapp.viewmodels.entities.Item

class ItemListFragment : Fragment(R.layout.item_list_fragment) {

    lateinit var itemListView: RecyclerView
    val storageViewModel: StorageModel by activityViewModels()
    val itemViewModel: ItemModel by activityViewModels()
    val categoryViewModel: CategoryModel by activityViewModels()

    var items: List<Item> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_add_item).setOnClickListener {
            itemViewModel.setCurrentItem(null)
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.item_frag_container, ItemFormFragment())
                addToBackStack(null)
            }
        }

        view.findViewById<Button>(R.id.btn_item_sort).setOnClickListener {
            ItemSortDialog(itemViewModel, object : FilterChangedCallback {
                override fun onChange() {
                    update()
                }
            }).show(childFragmentManager, "SortDialogTag")
        }

        itemListView = view.findViewById(R.id.item_list_view)
        itemListView.setHasFixedSize(true)
        itemListView.layoutManager = LinearLayoutManager(context)

        itemViewModel.liveList.observe(viewLifecycleOwner, Observer {
            items = it;
            update()
        })
        itemListView.adapter = ItemListAdapter(items, storageViewModel, categoryViewModel, itemViewModel, parentFragmentManager)

        itemViewModel.getItems()
    }

    fun update() {
        itemListView.adapter = ItemListAdapter(items, storageViewModel, categoryViewModel, itemViewModel, parentFragmentManager)
    }

    interface FilterChangedCallback {
        fun onChange()
    }
}