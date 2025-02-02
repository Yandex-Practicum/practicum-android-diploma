package ru.practicum.android.diploma.ui.filter.region.fragment

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemListBinding
import ru.practicum.android.diploma.domain.models.CountryRegionData

class RegionViewHolder(
    private val binding: ItemListBinding,
    private val onRegionClick: (countryRegionData: CountryRegionData) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: CountryRegionData) {
        binding.itemText.text = model.regionName
        binding.root.setOnClickListener { _ ->
            onRegionClick(model)
        }
    }
}
