package ru.practicum.android.diploma.ui.search.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

object VacancyDiffItemCallback : DiffUtil.ItemCallback<Vacancy>() {
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem.id == newItem.id
    }
}
