package ru.practicum.android.diploma.search.ui

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.core.domain.model.ShortVacancy

class VacancyDiffCallback(
    private val oldList: List<ShortVacancy>, private val newList: List<ShortVacancy>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
