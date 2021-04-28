package com.freezyapp.activities.ui.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.freezyapp.R
import com.freezyapp.activities.ui.categories.fragements.CategoryListFragment

class CategoryFragment : Fragment(R.layout.fragment_storage) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.category_frag_container, CategoryListFragment())
            addToBackStack(null)
        }
    }
}