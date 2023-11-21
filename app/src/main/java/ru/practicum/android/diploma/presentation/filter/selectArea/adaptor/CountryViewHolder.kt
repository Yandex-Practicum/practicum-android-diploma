package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.CountryItemBinding
import ru.practicum.android.diploma.domain.models.filter.Country


class CountryViewHolder(private val binding: CountryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Country) {
        binding.countryItemTextview.text = item.name
    }
}