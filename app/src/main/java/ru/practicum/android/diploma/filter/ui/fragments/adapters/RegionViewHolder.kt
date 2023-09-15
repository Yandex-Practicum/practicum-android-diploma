package ru.practicum.android.diploma.filter.ui.fragments.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemRegionDepartFilterBinding
import ru.practicum.android.diploma.filter.domain.models.Region

class RegionViewHolder(
    private val binding: ItemRegionDepartFilterBinding
) : RecyclerView.ViewHolder(binding.root) {
    
    val radioBtn = binding.radioBtn
    
    fun bind(model: Region, state: Boolean) {
        binding.region.text = model.name
        binding.radioBtn.isChecked = state
    }
}