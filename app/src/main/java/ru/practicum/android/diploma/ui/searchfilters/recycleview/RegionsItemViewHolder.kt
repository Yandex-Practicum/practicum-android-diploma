package ru.practicum.android.diploma.ui.searchfilters.recycleview

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemWorkplaceSelectableBinding
import ru.practicum.android.diploma.domain.models.filters.Region

class RegionsItemViewHolder(private val binding: ItemWorkplaceSelectableBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Region) {
        binding.tvWorkplaceName.text = item.name
    }
}
