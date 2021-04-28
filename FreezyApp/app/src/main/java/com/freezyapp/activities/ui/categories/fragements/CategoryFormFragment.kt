package com.freezyapp.activities.ui.categories.fragements

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
import com.freezyapp.viewmodels.CategoryModel
import com.freezyapp.viewmodels.entities.Category
import com.freezyapp.viewmodels.entities.Storage_Unit
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnFastChooseColorListener
import java.lang.String

class CategoryFormFragment : Fragment(R.layout.category_form_fragment) {

    lateinit var colorDisplay: MockView
    val categoryViewModel: CategoryModel by activityViewModels()

    lateinit var nameEdt: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEdt = view.findViewById(R.id.edt_category_name)

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

        colorDisplay = view.findViewById(R.id.category_color_view)
        colorDisplay.setOnClickListener {
            picker.show()
        }

        if (categoryViewModel.getCurrentCategory() != null) {
            view.findViewById<TextView>(R.id.category_form_title).text = getString(R.string.edit_category_title)

            colorDisplay.setBackgroundColor(Color.parseColor(categoryViewModel.getCurrentCategory()!!.getColor()))
            nameEdt.setText(categoryViewModel.getCurrentCategory()!!.getName())

            picker.setDefaultColorButton(Color.parseColor(categoryViewModel.getCurrentCategory()!!.getColor()))

            view.findViewById<Button>(R.id.btn_create_category).text = getString(R.string.edit)
        }

        view.findViewById<Button>(R.id.btn_cancel_category).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.btn_create_category).setOnClickListener {
            if (categoryViewModel.getCurrentCategory() != null) {
                //Edit
                val category: Category = categoryViewModel.getCurrentCategory()!!
                category.setName(nameEdt.text.toString())
                val newColor = String.format("#%06X", 0xFFFFFF and (colorDisplay.background as ColorDrawable).color)
                category.setColor(newColor)
                categoryViewModel.updateCategory(category)
                categoryViewModel.getAllCategories()
            }
            else {
                //new
                val newColor = String.format("#%06X", 0xFFFFFF and (colorDisplay.background as ColorDrawable).color)
                categoryViewModel.createCategory(nameEdt.text.toString(), newColor)
                categoryViewModel.getAllCategories()
            }


            parentFragmentManager.popBackStack()
        }
    }
}