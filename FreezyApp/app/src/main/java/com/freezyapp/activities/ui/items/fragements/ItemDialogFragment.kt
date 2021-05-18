package com.freezyapp.activities.ui.items.fragements

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.freezyapp.R
import com.freezyapp.activities.ui.storage.fragements.ItemFormFragment
import com.freezyapp.viewmodels.ItemModel

class ItemDialogFragment(private val itemViewModel: ItemModel, private val parentFragManager: FragmentManager) : DialogFragment(
    R.layout.item_dialog_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val oldQuantityLabel = view.findViewById<TextView>(R.id.dialog_old_quantity)
        oldQuantityLabel.text = itemViewModel.getCurrentItem()!!.getQuantity().toString() + " " + itemViewModel.getCurrentItem()!!.getUnit()

        val quantityField = view.findViewById<EditText>(R.id.dialog_item_new_quantity)

        view.findViewById<Button>(R.id.btn_update_item).setOnClickListener {
            itemViewModel.getCurrentItem()!!.setQuantity(quantityField.text.toString().toDouble())
            itemViewModel.updateItem(itemViewModel.getCurrentItem()!!)
            oldQuantityLabel.text = itemViewModel.getCurrentItem()!!.getQuantity().toString() + " " + itemViewModel.getCurrentItem()!!.getUnit()
        }

        view.findViewById<Button>(R.id.btn_delete_item).setOnClickListener {
            Log.d("ID", itemViewModel.getCurrentItem()!!.getId()!!.toString())
            itemViewModel.deleteItem(itemViewModel.getCurrentItem()!!.getId()!!)
            itemViewModel.resetItemList()
            parentFragManager.popBackStack()
        }

        view.findViewById<Button>(R.id.btn_edit_item).setOnClickListener {
            this.dismiss()
            parentFragManager.commit {
                setReorderingAllowed(true)
                replace(R.id.item_frag_container, ItemFormFragment())
                addToBackStack(null)
            }
        }
    }
}