package ru.practicum.android.diploma.search.presentation.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.databinding.ItemFilterIndustryBinding
import ru.practicum.android.diploma.search.domain.models.Industry

class IndustryListViewHolder(
    private val binding: ItemFilterIndustryBinding
) : RecyclerView.ViewHolder(binding.root) {
    val circleIcon = binding.circleIcon
    @SuppressLint("SetTextI18n")
    fun bind(model: Industry) {
        binding.optionText.text = model.name
    } }
