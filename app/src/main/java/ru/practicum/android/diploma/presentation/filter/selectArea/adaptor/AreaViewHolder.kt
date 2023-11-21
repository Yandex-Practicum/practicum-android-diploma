package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemAreaBinding
import ru.practicum.android.diploma.domain.models.filter.Area

class AreaViewHolder(val binding: ItemAreaBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Area) {
        binding.countryItemTextview.text = item.name
    }
}