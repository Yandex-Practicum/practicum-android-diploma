package ru.practicum.android.diploma.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.VacancyCard

class VacancyDiffCallback(
    private val oldList: List<VacancyCard>,
    private val newList: List<VacancyCard>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].vacancyId == newList[newItemPosition].vacancyId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
