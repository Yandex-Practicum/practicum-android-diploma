package ru.practicum.android.diploma.filter.presentation.ui.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.item.Industry
import ru.practicum.android.diploma.filter.databinding.ItemFilterIndustryBinding

class IndustryListViewHolder(
    private val binding: ItemFilterIndustryBinding
) : RecyclerView.ViewHolder(binding.root) {
    val circleIcon = binding.circleIcon
    @SuppressLint("SetTextI18n")
    fun bind(model: Industry) {
        binding.optionText.text = model.name
    } }
