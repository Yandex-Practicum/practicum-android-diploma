package ru.practicum.android.diploma.search.ui.fragment

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacancyItemDiffCallback : DiffUtil.ItemCallback<Vacancy>() {
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem.id == newItem.id
    }
    
    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }
}