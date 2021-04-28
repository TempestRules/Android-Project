package com.freezyapp.activities.ui.items.fragements

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.freezyapp.R
import com.freezyapp.viewmodels.ItemModel

class ItemDialogFragment(private val itemViewModel: ItemModel, private val parentFragManager: FragmentManager) : DialogFragment(
    R.layout.item_dialog_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}