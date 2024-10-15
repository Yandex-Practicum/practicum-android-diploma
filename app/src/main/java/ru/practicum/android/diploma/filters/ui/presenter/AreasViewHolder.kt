package ru.practicum.android.diploma.filters.ui.presenter

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaCardBinding
import ru.practicum.android.diploma.filters.domain.models.Area

class AreasViewHolder(private val binding: AreaCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(area: Area) {
        binding.area.text = area.name
    }
}
