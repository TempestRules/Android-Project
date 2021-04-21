package com.freezyapp.activities.ui.storage.fragements

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.utils.widget.MockView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.freezyapp.R
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnFastChooseColorListener

class StorageFormFragment : Fragment(R.layout.storage_form_fragment) {

    lateinit var colorDisplay: MockView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorDisplay = view.findViewById(R.id.storage_color_view)
        colorDisplay.setOnClickListener {
            val picker = ColorPicker(activity)
            picker.setColumns(5)
            picker.setOnFastChooseColorListener(object: OnFastChooseColorListener {
                override fun setOnFastChooseColorListener(position: Int, color: Int) {
                    colorDisplay.setBackgroundColor(color)
                }

                override fun onCancel() {

                }

            })
            picker.show()
        }

        view.findViewById<Button>(R.id.btn_cancel_storage).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.btn_create_storage).setOnClickListener {
            //TODO: Create Actual Storage in backend
            parentFragmentManager.popBackStack()
        }
    }
}