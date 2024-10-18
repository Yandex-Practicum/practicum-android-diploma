package ru.practicum.android.diploma.filters.areas.ui.presenter

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaCardBinding
import ru.practicum.android.diploma.filters.areas.domain.models.Area

class AreaViewHolder(private val binding: AreaCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(area: Area) {
        binding.area.text = area.name
    }
}
