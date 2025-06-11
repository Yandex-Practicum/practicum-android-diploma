package ru.practicum.android.diploma.ui.main.utils

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

class VacancyCallback(
    private val oldList: List<Vacancy>,
    private val newList: List<Vacancy>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
