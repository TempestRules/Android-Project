package com.freezyapp.activities.ui.storage.fragements

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freezyapp.R

class StorageListFragment : Fragment(R.layout.storage_list_fragment) {

    lateinit var storageListView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_add_storage).setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.storage_frag_container, StorageFormFragment())
                addToBackStack(null)
            }
        }

        storageListView = view.findViewById(R.id.storage_list_view)
        storageListView.setHasFixedSize(true)
        storageListView.layoutManager = LinearLayoutManager(context)
    }
}