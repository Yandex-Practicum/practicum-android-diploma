package ru.practicum.android.diploma.filter.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemFilterIndustryBinding
import ru.practicum.android.diploma.filter.domain.model.Industry

class IndustryViewHolder(
    val binding: ItemFilterIndustryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Industry) {
        binding.tvIndustryValue.text = model.name
        binding.rbIndustryButton.isChecked = model.selected
    }
}
