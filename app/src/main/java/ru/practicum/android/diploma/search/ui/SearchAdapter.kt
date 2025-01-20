package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.search.domain.model.Vacancy

class SearchAdapter(
    private val onItemClicked: (vacancy: Vacancy) -> Unit,
) : RecyclerView.Adapter<SearchItemViewHolder>() {

    private var vacancyList: List<Vacancy> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = ItemVacancyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchItemViewHolder(binding) { position ->
            if (position != RecyclerView.NO_POSITION) {
                vacancyList.getOrNull(position)?.let { vacancy ->
                    onItemClicked(vacancy)
                }
            }
        }
    }

    override fun getItemCount(): Int = vacancyList.size

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        vacancyList.getOrNull(position)?.let { vacancy ->
            holder.bind(vacancy)

        }
    }

    fun updateItems(items: List<Vacancy>) {
        val oldItems = this.vacancyList
        val newItemsList = items.toMutableList()
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldItems.size

            override fun getNewListSize(): Int = newItemsList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition].id == newItemsList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItemsList[newItemPosition]
            }
        })
        this.vacancyList = newItemsList
        diffResult.dispatchUpdatesTo(this)
    }
}
