package ru.practicum.android.diploma.util.diffutils

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.ui.filter.region.viewmodel.CountryRegionData

class CountryRegionDiffCallback : DiffUtil.ItemCallback<CountryRegionData>() {
    override fun areItemsTheSame(oldItem: CountryRegionData, newItem: CountryRegionData): Boolean {
        return oldItem.regionId == newItem.regionId
    }

    override fun areContentsTheSame(oldItem: CountryRegionData, newItem: CountryRegionData): Boolean {
        return oldItem == newItem
    }
}
