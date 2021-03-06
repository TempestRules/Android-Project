package com.freezyapp.activities.ui.items.fragements

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.freezyapp.R
import com.freezyapp.activities.ui.storage.fragements.ItemListFragment
import com.freezyapp.viewmodels.ItemModel

class ItemSortDialog(private val itemViewModel: ItemModel, private val filterChangeCallback: ItemListFragment.FilterChangedCallback) : DialogFragment(R.layout.sort_dialog_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sortStorage = view.findViewById<CheckBox>(R.id.sort_storage_chk)
        sortStorage.isChecked = itemViewModel.getSortStorage()
        sortStorage.setOnCheckedChangeListener { buttonView, isChecked ->
            itemViewModel.setSortStorage(isChecked)
            filterChangeCallback.onChange()
        }

        val sortCategory = view.findViewById<CheckBox>(R.id.sort_category_chk)
        sortCategory.isChecked = itemViewModel.getSortCategory()
        sortCategory.setOnCheckedChangeListener { buttonView, isChecked ->
            itemViewModel.setSortCategory(isChecked)
            filterChangeCallback.onChange()
        }

        val sortExpired = view.findViewById<CheckBox>(R.id.show_expired_chk)
        sortExpired.isChecked = itemViewModel.getOnlyExpired()
        sortExpired.setOnCheckedChangeListener { buttonView, isChecked ->
            itemViewModel.setOnlyExpired(isChecked)
            filterChangeCallback.onChange()
        }
    }
}