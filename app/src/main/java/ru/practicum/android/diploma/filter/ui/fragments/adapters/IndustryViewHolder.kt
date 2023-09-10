package ru.practicum.android.diploma.filter.ui.fragments.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemRegionDepartFilterBinding
import ru.practicum.android.diploma.filter.domain.models.Industry

class IndustryViewHolder(
    private val binding: ItemRegionDepartFilterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Industry, state: Boolean) {
        binding.region.text = model.name
        binding.radioBtn.isChecked = state
    }


}