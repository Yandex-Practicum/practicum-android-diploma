package ru.practicum.android.diploma.ui.industry

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryViewBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(private val binding: IndustryViewBinding) : RecyclerView.ViewHolder(binding.root) {
    val checkBox = binding.cbSelectIndustry

    fun bind(item: Industry) {
        binding.tvIndustryName.text = item.name
        binding.cbSelectIndustry.isChecked = item.isSelected
    }
}
