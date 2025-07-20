package ru.practicum.android.diploma.ui.searchfilters.recycleview

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemWorkplaceSelectableBinding
import ru.practicum.android.diploma.domain.models.filters.Country

class WorkplaceItemViewHolder(private val binding: ItemWorkplaceSelectableBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Country) {
        binding.tvWorkplaceName.text = item.name
    }
}
