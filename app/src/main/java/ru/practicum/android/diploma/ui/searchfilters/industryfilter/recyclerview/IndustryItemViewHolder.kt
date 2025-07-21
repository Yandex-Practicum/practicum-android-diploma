package ru.practicum.android.diploma.ui.searchfilters.industryfilter.recyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.domain.models.filters.Industry

class IndustryItemViewHolder(private val binding: ItemIndustryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Industry, isSelected: Boolean, clickListener: (String) -> Unit) {
        binding.industryText.text = item.name
        binding.radioBtn.setImageResource(
            if (isSelected) {
                R.drawable.radio_icon_clicked
            } else {
                R.drawable.radio_icon
            }
        )
        binding.root.setOnClickListener {
            clickListener(item.id)
        }
    }
}
