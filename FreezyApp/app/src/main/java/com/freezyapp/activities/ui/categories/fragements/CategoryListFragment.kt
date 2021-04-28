package com.freezyapp.activities.ui.categories.fragements

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freezyapp.R
import com.freezyapp.activities.ui.categories.CategoryListAdapter
import com.freezyapp.viewmodels.CategoryModel

class CategoryListFragment : Fragment(R.layout.category_list_fragment) {

    lateinit var categoryListView: RecyclerView
    val categoryViewModel: CategoryModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_add_category).setOnClickListener {
            categoryViewModel.setCurrentCategory(null)
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.category_frag_container, CategoryFormFragment())
                addToBackStack(null)
            }
        }

        categoryListView = view.findViewById(R.id.category_list_view)
        categoryListView.setHasFixedSize(true)
        categoryListView.layoutManager = LinearLayoutManager(context)

        categoryViewModel.liveList.observe(viewLifecycleOwner, Observer {
            categoryListView.adapter = CategoryListAdapter(it, categoryViewModel, parentFragmentManager)
        })

        categoryViewModel.getAllCategories()
    }
}