package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryAreaItemBinding
import ru.practicum.android.diploma.domain.models.filter.Industry


class IndystryViewHolder(val binding: IndustryAreaItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Industry) {
        binding.industryRegionTitleTextview.text = item.name
        binding.industryRegionCheckRadiobutton.isClickable = false
        binding.industryRegionCheckRadiobutton.isChecked = item.isChecked
    }
}
