package ru.practicum.android.diploma.ui.country

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FiltersItemBinding
import ru.practicum.android.diploma.domain.country.Country

class CountryViewHolder(private val binding: FiltersItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country) {
        binding.textView.text = country.name
        val textColor = ContextCompat.getColor(itemView.context, R.color.black)
        binding.textView.setTextColor(textColor)
    }
}
