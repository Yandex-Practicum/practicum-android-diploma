package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemLoadingBinding
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.search.presentation.list_items.ListItem

class SearchAdapter(
    private val onItemClicked: (listItem: ListItem) -> Unit,
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(ListItemDiffCallBack()) {

    //    private var vacancyList: MutableList<ListItem> = mutableListOf()
    private var isLoading = false

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.Vacancy -> TYPE_VACANCY
            is ListItem.LoadingItem -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_VACANCY -> {
                val binding = ItemVacancyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SearchItemViewHolder(binding) { position ->
                    if (position != RecyclerView.NO_POSITION) {
                        getItem(position)?.let { vacancy ->
                            onItemClicked(vacancy)
                        }
                    }
                }
            }

            TYPE_LOADING -> {
                val binding = ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LoadingViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    fun submitData(data: List<ListItem>) {
        val updatedList = currentList.toMutableList().apply {
            addAll(data)
        }
        submitList(updatedList)
    }


    fun showLoading() {
        if (!isLoading) {
            isLoading = true
            val updatedList = currentList.toMutableList().apply {
                add(ListItem.LoadingItem)
            }
            submitList(updatedList)
        }
    }


    fun hideLoading() {
        if (isLoading) {
            isLoading = false
            val updatedList = currentList.toMutableList().apply {
                removeAll { it is ListItem.LoadingItem }
            }
            submitList(updatedList)
        }
    }

//    override fun getItemCount(): Int = vacancyList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchItemViewHolder -> {
                val vacancy = getItem(position) as ListItem.Vacancy
                vacancy?.let { vacancyItems ->
                    holder.bind(vacancyItems)
                }
            }

            is LoadingViewHolder -> {}
        }
    }

//    fun showLoading() {
//        vacancyList.add(ListItem.LoadingItem)
//        notifyItemRemoved(vacancyList.size - 1)
//    }
//
//    fun hideLoading() {
//        val position = vacancyList.indexOfLast { it is ListItem.LoadingItem }
//        if (position != -1) {
//            vacancyList.removeAt(position)
//            notifyItemRemoved(position)
//        }
//    }

//    fun updateItems(items: List<ListItem>) {
//        val oldItems = this.vacancyList
//        val updatedList = items.toMutableList()
//        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
//            override fun getOldListSize(): Int = oldItems.size
//
//            override fun getNewListSize(): Int = updatedList.size
//
//            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                val oldItem = oldItems[oldItemPosition]
//                val newItem = updatedList[newItemPosition]
//                return when {
//                    oldItem is ListItem.Vacancy && newItem is ListItem.Vacancy ->
//                        oldItem.id == newItem.id
//                    oldItem is ListItem.LoadingItem && newItem is ListItem.LoadingItem -> true
//                    else -> false
//                }
//            }
//
//            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                return oldItems[oldItemPosition] == updatedList[newItemPosition]
//            }
//        })
//
//        diffResult.dispatchUpdatesTo(this)
//        this.vacancyList = updatedList
//    }

    companion object {
        private const val TYPE_VACANCY = 0
        private const val TYPE_LOADING = 1
    }
}

