package ru.practicum.android.diploma.ui.searchfilters.industryfilter.recyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.domain.models.industries.Industry

class IndustryItemViewHolder(private val binding: ItemIndustryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Industry) {
        binding.industryText.text = item.name
    }
}
