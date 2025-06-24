package ru.practicum.android.diploma.ui.filter.place.country.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.filter.place.models.Country

class CountryViewHolder private constructor(itemView: View) : ViewHolder(itemView) {
    private val nameCountry: TextView = itemView.findViewById(R.id.list_location_item)

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context).inflate(R.layout.list_location_item, parent, false)
    )

    fun bind(country: Country) {
        nameCountry.text = country.name
    }
}
