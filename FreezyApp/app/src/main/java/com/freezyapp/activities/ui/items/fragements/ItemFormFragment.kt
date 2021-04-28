package com.freezyapp.activities.ui.storage.fragements

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

        haveExpiration = view.findViewById<CheckBox>(R.id.item_expiration_chk)
        haveExpiration.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                expirationDate.layoutParams = ViewGroup.LayoutParams(expirationDate.width, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            else {
                expirationDate.layoutParams = ViewGroup.LayoutParams(expirationDate.width, 1)
            }
        }

        val storageObserve: Observer<List<Storage_Unit>> = Observer {
            storageList = it
            val adapter = StorageSpinnerAdapter(requireContext(), it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            storageSpinner.adapter = adapter
            storageViewModel.liveList.removeObservers(context as AppCompatActivity)
        }
        val categoryObserve: Observer<List<Category>> = Observer {
            categoryList = it
            val adapter = CategorySpinnerAdapter(requireContext(), it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
            categoryViewModel.liveList.removeObservers(context as AppCompatActivity)
        }

        storageViewModel.liveList.observe(viewLifecycleOwner, storageObserve)
        categoryViewModel.liveList.observe(viewLifecycleOwner, categoryObserve)

        storageViewModel.getAllStorages()
        categoryViewModel.getAllCategories()

        if (itemViewModel.getCurrentItem() != null) {
            val current: Item = itemViewModel.getCurrentItem()!!

            view.findViewById<TextView>(R.id.storage_form_title).text = getString(R.string.edit_item_title)

            nameEdt.setText(current.getName())
            unitEdt.setText(current.getUnit())

            if (current.getExpiration_date() != null) {
                haveExpiration.isChecked = true

                val expDate = Calendar.getInstance()
                expDate.clear()
                expDate.set(current.getExpiration_date()!!.year, current.getExpiration_date()!!.monthValue, current.getExpiration_date()!!.dayOfMonth)

                expirationDate.updateDate(expDate.get(Calendar.YEAR), expDate.get(Calendar.MONTH), expDate.get(Calendar.DAY_OF_MONTH))

                val unit = Storage_Unit()
                unit.setId(current.getStorageUnitId())
                storageSpinner.setSelection(storageList.indexOf(unit))

                val cat = Category()
                cat.setId(current.getCategoryIds()[0])
                categorySpinner.setSelection(categoryList.indexOf(cat))
            }

            view.findViewById<Button>(R.id.btn_create_item).text = getString(R.string.edit)
        }

        view.findViewById<Button>(R.id.btn_cancel_item).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.btn_create_item).setOnClickListener {
            if (itemViewModel.getCurrentItem() != null) {
                //Edit
                val item: Item = itemViewModel.getCurrentItem()!!
                item.setName(nameEdt.text.toString())
                item.setUnit(unitEdt.text.toString())

                if (haveExpiration.isChecked) {
                    val expireDate = LocalDateTime.of(expirationDate.year, expirationDate.month, expirationDate.dayOfMonth, 0, 0)
                    item.setExpiration_date(expireDate)
                }
                else {
                    item.setExpiration_date(null)
                }

                val storageId = storageList[storageSpinner.selectedItemPosition].getId()
                item.setStorageUnitId(storageId as Long)

                val categoryId = categoryList[categorySpinner.selectedItemPosition].getId()
                item.setCategory(arrayOf(categoryId).toList() as List<Long>)

                itemViewModel.updateItem(item)
                itemViewModel.getItems()
            }
            else {
                //new
                val expireDate = LocalDateTime.of(expirationDate.year, expirationDate.month, expirationDate.dayOfMonth, 0, 0)

                val storageId = storageList[storageSpinner.selectedItemPosition].getId()
                val categoryId = categoryList[categorySpinner.selectedItemPosition].getId()

                itemViewModel.createItem(nameEdt.text.toString(), expireDate, unitEdt.text.toString(), storageId as Long, 0.0, arrayOf(categoryId).toList() as List<Long>)
                itemViewModel.getItems()
            }


            parentFragmentManager.popBackStack()
        }
    }


}