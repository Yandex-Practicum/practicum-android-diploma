package ru.practicum.android.diploma.filter.profession.presentation.ui.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.filter.databinding.ItemFilterIndustryBinding
import ru.practicum.android.diploma.filter.profession.domain.model.IndustryModel

class IndustryListViewHolder(
    private val binding: ItemFilterIndustryBinding
) : RecyclerView.ViewHolder(binding.root) {
    val circleIcon = binding.circleIcon
    @SuppressLint("SetTextI18n")
    fun bind(model: IndustryModel) {
        binding.optionText.text = model.name
    } }
