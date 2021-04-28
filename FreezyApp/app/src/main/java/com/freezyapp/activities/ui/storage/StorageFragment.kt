package com.freezyapp.activities.ui.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.freezyapp.R
import com.freezyapp.activities.ui.storage.fragements.ItemListFragment
import com.freezyapp.activities.ui.storage.fragements.StorageListFragment

class StorageFragment : Fragment(R.layout.fragment_storage) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.storage_frag_container, StorageListFragment())
            addToBackStack(null)
        }
    }
}