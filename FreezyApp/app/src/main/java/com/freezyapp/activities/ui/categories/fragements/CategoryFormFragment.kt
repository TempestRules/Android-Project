package com.freezyapp.activities.ui.categories.fragements

import android.app.AlertDialog
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
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnFastChooseColorListener

class CategoryFormFragment : Fragment(R.layout.category_form_fragment) {

    lateinit var colorDisplay: MockView
    val categoryViewModel: CategoryModel by activityViewModels()

    lateinit var nameEdt: EditText

    var colorChosen: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEdt = view.findViewById(R.id.edt_category_name)


        var colorPicker: ColorPicker? = null
        colorDisplay = view.findViewById(R.id.category_color_view)
        colorDisplay.setOnClickListener {
            colorPicker = ColorPicker(activity)
            colorPicker!!.setColumns(5)
            if (categoryViewModel.getCurrentCategory() != null) {
                colorPicker!!.setDefaultColorButton(Color.parseColor(categoryViewModel.getCurrentCategory()!!.getColor()))
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

        if (categoryViewModel.getCurrentCategory() != null) {
            view.findViewById<TextView>(R.id.category_form_title).text = getString(R.string.edit_category_title)

            colorDisplay.setBackgroundColor(Color.parseColor(categoryViewModel.getCurrentCategory()!!.getColor()))
            colorChosen = true
            nameEdt.setText(categoryViewModel.getCurrentCategory()!!.getName())

            view.findViewById<Button>(R.id.btn_create_category).text = getString(R.string.edit)
        }

        view.findViewById<Button>(R.id.btn_cancel_category).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.btn_create_category).setOnClickListener {
            if (validInput()) {
                if (categoryViewModel.getCurrentCategory() != null) {
                    //Edit
                    val category: Category = categoryViewModel.getCurrentCategory()!!
                    category.setName(nameEdt.text.toString())
                    val newColor = String.format("#%06X", 0x00FFFFFF and (colorDisplay.background as ColorDrawable).color)
                    category.setColor(newColor)
                    categoryViewModel.updateCategory(category)
                }
                else {
                    //new
                    val newColor = String.format("#%06X", 0x00FFFFFF and (colorDisplay.background as ColorDrawable).color)
                    categoryViewModel.createCategory(nameEdt.text.toString(), newColor)
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