package ru.practicum.android.diploma.ui.filter.place.region.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.ui.filter.place.models.Region

class RegionListCallback(
    private val oldList: List<Region>,
    private val newList: List<Region>
) : DiffUtil.Callback() {
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (id, name) = oldList[oldItemPosition]
        val (id1, name1) = newList[newItemPosition]
        return id == id1 && name == name1
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    override fun getNewListSize(): Int = newList.size

    override fun getOldListSize(): Int = oldList.size
}
