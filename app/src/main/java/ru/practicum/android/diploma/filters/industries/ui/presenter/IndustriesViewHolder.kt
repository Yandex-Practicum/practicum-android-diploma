package ru.practicum.android.diploma.filters.industries.ui.presenter

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryCardBinding
import ru.practicum.android.diploma.filters.industries.domain.models.Industry

class IndustriesViewHolder(private val binding: IndustryCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(industry: Industry) {
        binding.industryItem.text = industry.name
    }

    fun selectItem() {
        binding.industryItem.isChecked = true
    }
}
