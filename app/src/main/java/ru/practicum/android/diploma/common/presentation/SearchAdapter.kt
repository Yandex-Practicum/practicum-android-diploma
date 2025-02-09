package ru.practicum.android.diploma.common.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemLoadingBinding
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.search.presentation.items.ListItem

class SearchAdapter(
    private val onItemClicked: (listItem: ListItem) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = mutableListOf<ListItem>()
    private var isLoading = false

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is ListItem.Vacancy -> TYPE_VACANCY
            is ListItem.LoadingItem -> TYPE_LOADING
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
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
                        itemList[position]?.let { vacancy ->
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
        itemList.addAll(data)
        notifyItemRangeInserted(itemList.size - data.size, data.size)
    }

    fun getCurrentList(): List<ListItem> {
        return itemList
    }

    fun showLoading() {
        itemList.add(ListItem.LoadingItem)
        notifyItemInserted(itemList.size - 1)
    }

    fun hideLoading() {
        val position = itemList.indexOf(ListItem.LoadingItem)
        if (position != RecyclerView.NO_POSITION) {
            itemList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clearData() {
        itemList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchItemViewHolder -> {
                val vacancy = itemList[position] as ListItem.Vacancy
                vacancy?.let { vacancyItems ->
                    holder.bind(vacancyItems)
                }
            }

            is LoadingViewHolder -> {}
        }
    }

    companion object {
        private const val TYPE_VACANCY = 0
        private const val TYPE_LOADING = 1
    }
}
