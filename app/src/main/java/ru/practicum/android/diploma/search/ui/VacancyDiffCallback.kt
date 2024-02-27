package ru.practicum.android.diploma.search.ui

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.core.domain.model.ShortVacancy

class VacancyDiffCallback : DiffUtil.ItemCallback<ShortVacancy>() {
    override fun areItemsTheSame(oldItem: ShortVacancy, newItem: ShortVacancy): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShortVacancy, newItem: ShortVacancy): Boolean {
        return oldItem == newItem
    }
}
