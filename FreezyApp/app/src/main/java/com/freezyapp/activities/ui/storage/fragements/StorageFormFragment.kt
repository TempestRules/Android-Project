package com.freezyapp.activities.ui.storage.fragements

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.utils.widget.MockView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.freezyapp.R
import com.freezyapp.viewmodels.StorageModel
import com.freezyapp.viewmodels.entities.Storage_Unit
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnFastChooseColorListener
import java.lang.String

class StorageFormFragment : Fragment(R.layout.storage_form_fragment) {

    lateinit var colorDisplay: MockView
    val storageViewModel: StorageModel by activityViewModels()

    lateinit var nameEdt: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEdt = view.findViewById(R.id.edt_storage_name)

        val picker = ColorPicker(activity)
        picker.setColumns(5)
        picker.setDefaultColorButton(Color.parseColor("#f84c44"))
        picker.setOnFastChooseColorListener(object: OnFastChooseColorListener {
            override fun setOnFastChooseColorListener(position: Int, color: Int) {
                colorDisplay.setBackgroundColor(color)
            }

            override fun onCancel() {

            }

        })

        colorDisplay = view.findViewById(R.id.storage_color_view)
        colorDisplay.setOnClickListener {
            picker.show()
        }

        if (storageViewModel.getCurrentStorageUnit() != null) {
            view.findViewById<TextView>(R.id.storage_form_title).text = getString(R.string.edit_storage_title)

            colorDisplay.setBackgroundColor(Color.parseColor(storageViewModel.getCurrentStorageUnit()!!.getColor()))
            nameEdt.setText(storageViewModel.getCurrentStorageUnit()!!.getName())

            picker.setDefaultColorButton(Color.parseColor(storageViewModel.getCurrentStorageUnit()!!.getColor()))

            view.findViewById<Button>(R.id.btn_create_storage).text = getString(R.string.edit)
        }

        view.findViewById<Button>(R.id.btn_cancel_storage).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.btn_create_storage).setOnClickListener {
            if (storageViewModel.getCurrentStorageUnit() != null) {
                //Edit
                val unit: Storage_Unit = storageViewModel.getCurrentStorageUnit()!!
                unit.setName(nameEdt.text.toString())
                val newColor = String.format("#%06X", 0xFFFFFF and (colorDisplay.background as ColorDrawable).color)
                unit.setColor(newColor)
                storageViewModel.updateStorage(unit)
                storageViewModel.getAllStorages()
            }
            else {
                //new
                val newColor = String.format("#%06X", 0xFFFFFF and (colorDisplay.background as ColorDrawable).color)
                storageViewModel.createStorage(nameEdt.text.toString(), newColor)
                storageViewModel.getAllStorages()
            }


            parentFragmentManager.popBackStack()
        }
    }
}