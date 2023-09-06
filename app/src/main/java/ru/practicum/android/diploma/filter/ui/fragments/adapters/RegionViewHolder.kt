package ru.practicum.android.diploma.filter.ui.fragments.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryFilterBinding
import ru.practicum.android.diploma.databinding.ItemRegionDepartFilterBinding
import ru.practicum.android.diploma.filter.domain.models.Region

class RegionViewHolder(
    private val binding: ItemRegionDepartFilterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Region) {
        binding.region.text = model.name
    }
}