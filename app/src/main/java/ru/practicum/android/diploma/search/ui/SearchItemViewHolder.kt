package ru.practicum.android.diploma.search.ui

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.search.domain.model.Vacancy

class SearchItemViewHolder(
    private val binding: ItemVacancyBinding,
    onItemClicked: (position: Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(vacancy: Vacancy){

    }
}
