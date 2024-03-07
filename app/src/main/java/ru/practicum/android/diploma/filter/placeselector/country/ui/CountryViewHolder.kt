package ru.practicum.android.diploma.filter.placeselector.country.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.Country

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.tvCountryOrRegion)

    fun bind(country: Country) {
        name.text = country.name
    }
}
