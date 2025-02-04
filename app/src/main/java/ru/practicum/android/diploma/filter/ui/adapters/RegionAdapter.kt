package ru.practicum.android.diploma.filter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemFilterRegionBinding
import ru.practicum.android.diploma.filter.domain.model.Area

class RegionAdapter(
    private val onItemClicked: (area: Area) -> Unit,
) : RecyclerView.Adapter<RegionViewHolder>() {

    private var areaList: List<Area> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding = ItemFilterRegionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RegionViewHolder(binding) { position: Int ->
            if (position != RecyclerView.NO_POSITION) {
                areaList.getOrNull(position)?.let { area: Area ->
                    onItemClicked(area)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return areaList.size
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        areaList.getOrNull(position)?.let { track ->
            holder.bind(track)
        }
    }

    fun updateItems(items: List<Area>) {
        val oldItems = this.areaList
        val newItems = items.toMutableList()
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition].id == newItems[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }
        })
        this.areaList = newItems
        diffResult.dispatchUpdatesTo(this)
    }
}
