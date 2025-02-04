package ru.practicum.android.diploma.filter.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemFilterRegionBinding
import ru.practicum.android.diploma.filter.domain.model.Area

class RegionViewHolder(
    private val binding: ItemFilterRegionBinding,
    onItemClicked: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(item: Area) {
        with(binding) {
            tvRegionValue.text = item.name
        }
    }
}
