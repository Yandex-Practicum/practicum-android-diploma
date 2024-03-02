package ru.practicum.android.diploma.ui.filter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.practicum.android.diploma.data.response.IndustryResponse
import ru.practicum.android.diploma.databinding.ListOfIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(val binding: ListOfIndustryBinding): ViewHolder(binding.root) {
    fun bind(item: Industry) {
        binding.department.text = item.name
    }
}
