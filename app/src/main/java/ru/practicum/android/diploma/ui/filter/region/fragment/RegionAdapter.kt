package ru.practicum.android.diploma.ui.filter.region.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ItemListBinding
import ru.practicum.android.diploma.domain.models.CountryRegionData
import ru.practicum.android.diploma.util.diffutils.CountryRegionDiffCallback

class RegionAdapter(
    private val onRegionClick: (countryRegionData: CountryRegionData) -> Unit,
) : ListAdapter<CountryRegionData, RegionViewHolder>(CountryRegionDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RegionViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RegionViewHolder(binding, onRegionClick)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val area = getItem(position)
        holder.bind(area)
    }
}
