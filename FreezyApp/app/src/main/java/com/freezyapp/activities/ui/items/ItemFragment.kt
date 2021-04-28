package com.freezyapp.activities.ui.items

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.freezyapp.R
import com.freezyapp.activities.ui.storage.fragements.ItemListFragment

class ItemFragment : Fragment(R.layout.fragment_item) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.item_frag_container, ItemListFragment())
            addToBackStack(null)
        }
    }
}