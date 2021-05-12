package com.freezyapp.activities.ui.storage.fragements

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freezyapp.R
import com.freezyapp.activities.ui.storage.StorageListAdapter
import com.freezyapp.viewmodels.StorageModel

class StorageListFragment : Fragment(R.layout.storage_list_fragment) {

    lateinit var storageListView: RecyclerView
    val storageViewModel: StorageModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_add_storage).setOnClickListener {
            storageViewModel.setCurrentStorageUnit(null)
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.storage_frag_container, StorageFormFragment())
                addToBackStack(null)
            }
        }

        storageListView = view.findViewById(R.id.storage_list_view)
        storageListView.setHasFixedSize(true)
        storageListView.layoutManager = LinearLayoutManager(context)

        storageViewModel.liveList.observe(viewLifecycleOwner, Observer {
            storageListView.adapter = StorageListAdapter(it, storageViewModel, parentFragmentManager)
        })

        storageViewModel.getAllStorages()
        Log.d("LICKMA", "BALLS")
    }
}