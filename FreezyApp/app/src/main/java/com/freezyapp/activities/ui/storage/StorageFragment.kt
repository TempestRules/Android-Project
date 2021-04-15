package com.freezyapp.activities.ui.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.freezyapp.R
import com.freezyapp.activities.ui.storage.fragements.StorageFormFragment

class StorageFragment : Fragment(R.layout.fragment_storage) {
    lateinit var fragmentContainer: FragmentContainerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentContainer = view.findViewById(R.id.storage_frag_container)
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.storage_frag_container, StorageFormFragment())
            addToBackStack(null)
        }
    }
}