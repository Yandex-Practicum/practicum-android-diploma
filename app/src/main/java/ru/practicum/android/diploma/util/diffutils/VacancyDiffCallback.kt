package ru.practicum.android.diploma.util.diffutils

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDiffCallback : DiffUtil.ItemCallback<Vacancy>() {
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem.vacancyId == newItem.vacancyId
    }

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }
}
