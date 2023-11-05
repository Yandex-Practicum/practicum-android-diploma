package ru.practicum.android.diploma.presentation.similar.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.mok.Vacancy

class VacancyDiffCallBack (
    private val oldList: List<Vacancy>,
    private val newList: List<Vacancy>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTrack = oldList[oldItemPosition]
        val newTrack = newList[newItemPosition]
        return oldTrack.name == newTrack.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTrack = oldList[oldItemPosition]
        val newTrack = newList[newItemPosition]
        return oldTrack == newTrack
    }
}