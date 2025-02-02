package ru.practicum.android.diploma.ui.filter.country.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ItemListBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.diffutils.AreaDiffCallback

class CountryAdapter(
    private val onCountryClick: (id: String, name: String) -> Unit,
) : ListAdapter<Area, CountryViewHolder>(AreaDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CountryViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding, onCountryClick)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val area = getItem(position)
        holder.bind(area)
    }
}
