package ru.practicum.android.diploma.ui.favorite.utils

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacanciesCallback(
    private val oldList: List<VacancyDetails>,
    private val newList: List<VacancyDetails>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].vacancy.id == newList[newItemPosition].vacancy.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
