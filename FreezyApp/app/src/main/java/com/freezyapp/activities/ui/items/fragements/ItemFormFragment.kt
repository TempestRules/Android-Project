package com.freezyapp.activities.ui.storage.fragements

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.freezyapp.R
import com.freezyapp.activities.ui.items.CategorySpinnerAdapter
import com.freezyapp.activities.ui.items.StorageSpinnerAdapter
import com.freezyapp.viewmodels.CategoryModel
import com.freezyapp.viewmodels.ItemModel
import com.freezyapp.viewmodels.StorageModel
import com.freezyapp.viewmodels.entities.Category
import com.freezyapp.viewmodels.entities.Item
import com.freezyapp.viewmodels.entities.Storage_Unit
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ItemFormFragment : Fragment(R.layout.item_form_fragment) {

    val storageViewModel: StorageModel by activityViewModels()
    val itemViewModel: ItemModel by activityViewModels()
    val categoryViewModel: CategoryModel by activityViewModels()

    lateinit var nameEdt: EditText
    lateinit var unitEdt: EditText
    lateinit var storageSpinner: Spinner
    lateinit var categorySpinner: Spinner
    lateinit var haveExpiration: CheckBox
    lateinit var expirationDate: DatePicker

    lateinit var storageList: List<Storage_Unit>
    lateinit var categoryList: List<Category>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEdt = view.findViewById(R.id.edt_item_name)
        unitEdt = view.findViewById(R.id.edt_item_unit)
        storageSpinner = view.findViewById(R.id.item_storage_spinner)
        categorySpinner = view.findViewById(R.id.item_category_spinner)
        expirationDate = view.findViewById(R.id.item_expiration_date)

        haveExpiration = view.findViewById(R.id.item_expiration_chk)
        haveExpiration.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val params: ViewGroup.LayoutParams = expirationDate.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                expirationDate.layoutParams = params
            }
            else {
                val params: ViewGroup.LayoutParams = expirationDate.layoutParams
                params.height = 1
                expirationDate.layoutParams = params
            }
        }

        val storageObserve: Observer<List<Storage_Unit>> = Observer {
            storageList = it
            val adapter = StorageSpinnerAdapter(requireContext(), it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            storageSpinner.adapter = adapter

            if (itemViewModel.getCurrentItem() != null) {
                val unit = Storage_Unit()
                unit.setId(itemViewModel.getCurrentItem()!!.getStorageUnitId())
                storageSpinner.setSelection(storageList.indexOf(unit))
            }

            storageViewModel.liveList.removeObservers(context as AppCompatActivity)
        }
        val categoryObserve: Observer<List<Category>> = Observer {
            categoryList = it
            val adapter = CategorySpinnerAdapter(requireContext(), it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter

            if (itemViewModel.getCurrentItem() != null) {
                val cat = Category()
                cat.setId(itemViewModel.getCurrentItem()!!.getCategoryIds()[0])
                categorySpinner.setSelection(categoryList.indexOf(cat))
            }

            categoryViewModel.liveList.removeObservers(context as AppCompatActivity)
        }

        storageViewModel.liveList.observe(viewLifecycleOwner, storageObserve)
        categoryViewModel.liveList.observe(viewLifecycleOwner, categoryObserve)

        storageViewModel.getAllStorages()
        categoryViewModel.getAllCategories()

        if (itemViewModel.getCurrentItem() != null) {
            val current: Item = itemViewModel.getCurrentItem()!!

            view.findViewById<TextView>(R.id.item_form_title).text = getString(R.string.edit_item_title)

            nameEdt.setText(current.getName())
            unitEdt.setText(current.getUnit())

            if (current.getExpiration_date() != null) {
                val curExpDate = LocalDateTime.parse(current.getExpiration_date(), DateTimeFormatter.ISO_DATE_TIME)

                haveExpiration.isChecked = true

                val expDate = Calendar.getInstance()
                expDate.clear()
                expDate.set(curExpDate.year, curExpDate.monthValue, curExpDate.dayOfMonth)

                expirationDate.updateDate(expDate.get(Calendar.YEAR), expDate.get(Calendar.MONTH) - 1, expDate.get(Calendar.DAY_OF_MONTH))
            }

            view.findViewById<Button>(R.id.btn_create_item).text = getString(R.string.edit)
        }

        view.findViewById<Button>(R.id.btn_cancel_item).setOnClickListener {
            if (itemViewModel.getCurrentItem() != null) {
                parentFragmentManager.popBackStack("dialog", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            else {
                parentFragmentManager.popBackStack()
            }
        }

        view.findViewById<Button>(R.id.btn_create_item).setOnClickListener {
            if (validInput()) {
                if (itemViewModel.getCurrentItem() != null) {
                    //Edit
                    val item: Item = itemViewModel.getCurrentItem()!!
                    item.setName(nameEdt.text.toString())
                    item.setUnit(unitEdt.text.toString())

                    if (haveExpiration.isChecked) {
                        val expireDate = LocalDateTime.of(expirationDate.year, expirationDate.month + 1, expirationDate.dayOfMonth, 0, 0)
                        item.setExpiration_date(expireDate.format(DateTimeFormatter.ISO_DATE_TIME))
                        Log.d("EXP", item.getExpiration_date()!!)
                    }
                    else {
                        item.setExpiration_date(null)
                    }

                    val storageId = storageList[storageSpinner.selectedItemPosition].getId()
                    item.setStorageUnitId(storageId as Long)

                    val categoryId = categoryList[categorySpinner.selectedItemPosition].getId()
                    item.setCategory(arrayOf(categoryId).toList() as List<Long>)

                    itemViewModel.updateItem(item)
                }
                else {
                    //new
                    var expireDate: LocalDateTime? = null

                    if (haveExpiration.isChecked) {
                        expireDate = LocalDateTime.of(expirationDate.year, expirationDate.month + 1, expirationDate.dayOfMonth, 0, 0)
                    }

                    val storageId = storageList[storageSpinner.selectedItemPosition].getId()
                    val categoryId = categoryList[categorySpinner.selectedItemPosition].getId()

                    itemViewModel.createItem(nameEdt.text.toString(), expireDate, unitEdt.text.toString(), storageId as Long, 0.0, arrayOf(categoryId).toList() as List<Long>)
                }


                if (itemViewModel.getCurrentItem() != null) {
                    parentFragmentManager.popBackStack("dialog", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
                else {
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun validInput(): Boolean {
        if (nameEdt.text.isEmpty()) {
            AlertDialog.Builder(context).setTitle("No Name").show()
            return false
        }
        else if (unitEdt.text.isEmpty()) {
            AlertDialog.Builder(context).setTitle("No Unit").show()
            return false
        }
        else if (storageList.isEmpty()) {
            AlertDialog.Builder(context).setTitle("No Storages").show()
            return false
        }
        else if (categoryList.isEmpty()) {
            AlertDialog.Builder(context).setTitle("No Categories").show()
            return false
        }

        return true
    }
}