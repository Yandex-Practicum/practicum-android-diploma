package ru.practicum.android.diploma.ui.country

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.RegionCellViewBinding
import ru.practicum.android.diploma.domain.country.Country

class CountryViewHolder(private val binding: RegionCellViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country) {
        binding.titleTextView.text = country.name
        val textColor = ContextCompat.getColor(itemView.context, R.color.black)
        binding.titleTextView.setTextColor(textColor)
    }
}
