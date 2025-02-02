package ru.practicum.android.diploma.ui.filter.industry.fragment

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(
    private val binding: ItemIndustryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(industry: Industry, isSelected: Boolean, onItemSelected: (Industry) -> Unit) {
        binding.industryName.text = industry.name
        binding.selectionRadioButton.isChecked = isSelected

        binding.root.setOnClickListener {
            binding.selectionRadioButton.isChecked = true
            onItemSelected(industry)
        }

        binding.selectionRadioButton.setOnClickListener {
            onItemSelected(industry)
        }
    }
}
