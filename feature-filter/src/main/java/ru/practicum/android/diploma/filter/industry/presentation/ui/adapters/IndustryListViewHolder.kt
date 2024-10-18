package ru.practicum.android.diploma.filter.industry.presentation.ui.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.filter.databinding.ItemFilterIndustryBinding
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

class IndustryListViewHolder(
    private val binding: ItemFilterIndustryBinding
) : RecyclerView.ViewHolder(binding.root) {
    val circleIcon = binding.circleIcon
    @SuppressLint("SetTextI18n")
    fun bind(model: IndustryModel) {
        binding.optionText.text = model.name
    }
}
