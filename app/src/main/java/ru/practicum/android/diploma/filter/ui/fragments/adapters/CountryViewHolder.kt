package ru.practicum.android.diploma.filter.ui.fragments.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryFilterBinding
import ru.practicum.android.diploma.filter.domain.models.Country

class CountryViewHolder(
    private val binding: ItemCountryFilterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Country) {
        binding.country.text = model.name
    }
}