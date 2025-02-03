package ru.practicum.android.diploma.filter.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryRegionBinding
import ru.practicum.android.diploma.filter.domain.model.Country

class CountryViewHolder(
    val binding: ItemCountryRegionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Country) {
        binding.countryOrRegion.text = model.name
    }
}
