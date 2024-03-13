package ru.practicum.android.diploma.presentation.industries

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.industries.ParentIndustriesAllDeal

class IndustriesViewHolder(private val binding: IndustryItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(industries: ParentIndustriesAllDeal) {
        binding.textView.text = industries.name
        binding.checkBoxIndustry.isChecked = industries.isChecked
        val textColor = ContextCompat.getColor(itemView.context, R.color.black)
        binding.textView.setTextColor(textColor)
    }

    fun bindSelection(isSelected: Boolean) {
        binding.checkBoxIndustry.isChecked = isSelected
    }
}
