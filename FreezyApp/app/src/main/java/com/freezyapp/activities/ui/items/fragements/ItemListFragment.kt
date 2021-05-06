package com.freezyapp.activities.ui.storage.fragements

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freezyapp.R
import com.freezyapp.activities.ui.items.ItemListAdapter
import com.freezyapp.activities.ui.items.RecycleListItem
import com.freezyapp.activities.ui.items.fragements.ItemSortDialog
import com.freezyapp.activities.ui.items.recyclelistitems.CategoryRecycleListItem
import com.freezyapp.activities.ui.items.recyclelistitems.ItemRecycleListItem
import com.freezyapp.activities.ui.items.recyclelistitems.StorageRecyleListItem
import com.freezyapp.viewmodels.CategoryModel
import com.freezyapp.viewmodels.ItemModel
import com.freezyapp.viewmodels.StorageModel
import com.freezyapp.viewmodels.entities.Category
import com.freezyapp.viewmodels.entities.Item
import com.freezyapp.viewmodels.entities.Storage_Unit
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ItemListFragment : Fragment(R.layout.item_list_fragment) {

    lateinit var itemListView: RecyclerView
    val storageViewModel: StorageModel by activityViewModels()
    val itemViewModel: ItemModel by activityViewModels()
    val categoryViewModel: CategoryModel by activityViewModels()

    var items: List<Item> = emptyList()

    val liveItemList: MutableLiveData<MutableList<RecycleListItem>> = MutableLiveData()
    val itemList: LiveData<MutableList<RecycleListItem>> = liveItemList

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_add_item).setOnClickListener {
            itemViewModel.setCurrentItem(null)
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.item_frag_container, ItemFormFragment())
                addToBackStack(null)
            }
        }

        view.findViewById<Button>(R.id.btn_item_sort).setOnClickListener {
            ItemSortDialog(itemViewModel, object : FilterChangedCallback {
                override fun onChange() {
                    update()
                }
            }).show(childFragmentManager, "SortDialogTag")
        }

        itemListView = view.findViewById(R.id.item_list_view)
        itemListView.setHasFixedSize(true)
        itemListView.layoutManager = LinearLayoutManager(context)

        itemViewModel.liveList.observe(viewLifecycleOwner, Observer {
            items = it;
            update()
        })

        itemList.observe(viewLifecycleOwner, Observer {
            itemListView.adapter = ItemListAdapter(it, storageViewModel, categoryViewModel, itemViewModel, parentFragmentManager)
        })
        itemViewModel.getItems()
    }

    fun update() {
        generateRecycleList(items)

    }

    fun generateRecycleList(data: List<Item>) {
        var dataList = data
        if (itemViewModel.getOnlyExpired()) {
            dataList = dataList.filter{
                if (it.getExpiration_date() == null) false

                val expireDate = it.getExpiration_date()
                val today = LocalDateTime.now()
                val diff = expireDate!!.until(today, ChronoUnit.DAYS)

                diff < 0
            }
        }

        val storageObserve: Observer<List<Storage_Unit>> = Observer { storages ->
            val storageList = storages
            storageViewModel.liveList.removeObservers(context as AppCompatActivity)

            val categoryObserve: Observer<List<Category>> = Observer { categories ->
                val categoryList = categories
                categoryViewModel.liveList.removeObservers(context as AppCompatActivity)

                var recycleDataList = dataList.map { item -> ItemRecycleListItem(item, categoryList.find { cat -> cat.getId() == item.getCategoryIds()[0]}!!.getColor()!!) }

                if (itemViewModel.getSortStorage()) {
                    val dataMap: HashMap<Storage_Unit, MutableList<ItemRecycleListItem>> = HashMap()

                    storageList.forEach {
                        dataMap[it] = recycleDataList.filter { item -> item.getItem().getStorageUnitId() == it.getId() }.toMutableList()
                    }

                    if (itemViewModel.getSortCategory()) {
                        val doubleDataMap: HashMap<Storage_Unit, HashMap<Category, MutableList<ItemRecycleListItem>>> = HashMap()

                        dataMap.forEach {
                            doubleDataMap[it.key] = HashMap()

                            it.value.forEach {item ->
                                val category = categoryList.find { cat -> item.getItem().getCategoryIds()[0] == cat.getId() }!!
                                if (!doubleDataMap[it.key]!!.contains(category)) {
                                    doubleDataMap[it.key]!!.set(category, mutableListOf(item))
                                }
                                else {
                                    doubleDataMap[it.key]!![category]!!.plusAssign(item)
                                }
                            }
                        }

                        //DONE
                        val itemList: MutableList<RecycleListItem> = mutableListOf()
                        doubleDataMap.forEach {
                            itemList.add(StorageRecyleListItem(it.key))
                            itemList.addAll(generateCategoryList(it.value))
                        }
                        liveItemList.value = itemList
                        return@Observer
                    }

                    //DONE
                    liveItemList.value = generateStorageList(dataMap)
                    return@Observer
                }

                if (itemViewModel.getSortCategory()) {
                    //Only category
                    val dataMap: java.util.HashMap<Category, MutableList<ItemRecycleListItem>> = java.util.HashMap()

                    categoryList.forEach {
                        dataMap[it] = recycleDataList.filter { item -> item.getItem().getCategoryIds()[0] == it.getId() }.toMutableList()
                    }

                    //DONE
                    liveItemList.value = generateCategoryList(dataMap)
                    return@Observer
                }

                //DONE
                liveItemList.value = recycleDataList.toMutableList()
            }

            categoryViewModel.liveList.observe(viewLifecycleOwner, categoryObserve)
            categoryViewModel.getAllCategories()
        }

        storageViewModel.liveList.observe(viewLifecycleOwner, storageObserve)

        storageViewModel.getAllStorages()
    }

    fun generateCategoryList(data: java.util.HashMap<Category, MutableList<ItemRecycleListItem>>): MutableList<RecycleListItem> {
        val result = mutableListOf<RecycleListItem>()

        data.forEach{
            result.plusAssign(CategoryRecycleListItem(it.key))

            it.value.forEach{ item ->
                result.plusAssign(item)
            }
        }

        return result
    }

    fun generateStorageList(data: HashMap<Storage_Unit, MutableList<ItemRecycleListItem>>): MutableList<RecycleListItem> {
        val result = mutableListOf<RecycleListItem>()

        data.forEach {
            result.plusAssign(StorageRecyleListItem(it.key))

            it.value.forEach {item ->
                result.plusAssign(item)
            }
        }

        return result
    }

    interface FilterChangedCallback {
        fun onChange()
    }
}