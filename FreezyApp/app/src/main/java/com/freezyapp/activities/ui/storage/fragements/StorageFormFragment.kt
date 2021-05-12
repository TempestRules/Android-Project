package com.freezyapp.activities.ui.storage.fragements

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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

    var colorChosen: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEdt = view.findViewById(R.id.edt_storage_name)

        var colorPicker: ColorPicker? = null
        colorDisplay = view.findViewById(R.id.storage_color_view)
        colorDisplay.setOnClickListener {
            colorPicker = ColorPicker(activity)
            colorPicker!!.setColumns(5)
            if (storageViewModel.getCurrentStorageUnit() != null) {
                colorPicker!!.setDefaultColorButton(Color.parseColor(storageViewModel.getCurrentStorageUnit()!!.getColor()))
            }
            else {
                colorPicker!!.setDefaultColorButton(Color.parseColor("#f84c44"))
            }
            colorPicker!!.setOnFastChooseColorListener(object: OnFastChooseColorListener {
                override fun setOnFastChooseColorListener(position: Int, color: Int) {
                    colorDisplay.setBackgroundColor(color)
                    colorChosen = true
                }

                override fun onCancel() {
                    colorPicker!!.dismissDialog()
                }

            })
            colorPicker!!.show()
        }

        if (storageViewModel.getCurrentStorageUnit() != null) {
            view.findViewById<TextView>(R.id.storage_form_title).text = getString(R.string.edit_storage_title)

            colorDisplay.setBackgroundColor(Color.parseColor(storageViewModel.getCurrentStorageUnit()!!.getColor()))
            colorChosen = true
            nameEdt.setText(storageViewModel.getCurrentStorageUnit()!!.getName())

            view.findViewById<Button>(R.id.btn_create_storage).text = getString(R.string.edit)
        }

        view.findViewById<Button>(R.id.btn_cancel_storage).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.btn_create_storage).setOnClickListener {
            if (validInput()) {
                if (storageViewModel.getCurrentStorageUnit() != null) {
                    //Edit
                    val unit: Storage_Unit = storageViewModel.getCurrentStorageUnit()!!
                    unit.setName(nameEdt.text.toString())
                    val newColor = String.format("#%06X", 0x00FFFFFF and (colorDisplay.background as ColorDrawable).color)
                    unit.setColor(newColor)
                    storageViewModel.updateStorage(unit)
                }
                else {
                    //new
                    val newColor = String.format("#%06X", 0x00FFFFFF and (colorDisplay.background as ColorDrawable).color)
                    storageViewModel.createStorage(nameEdt.text.toString(), newColor)
                }


                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun validInput(): Boolean {
        if (nameEdt.text.isEmpty()) {
            AlertDialog.Builder(context).setTitle("No Name").show()
            return false
        }
        else if (!colorChosen) {
            AlertDialog.Builder(context).setTitle("No Color chosen").show()
            return false
        }

        return true
    }
}