package ru.practicum.android.diploma.ui.filter.workplace.country

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Country

class CountriesViewHolder(
    parent: ViewGroup,
    private val clickListener: CountriesAdapter.CountryClickListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.country_list_item, parent, false)
) {
    private val countryItem = itemView.findViewById<TextView>(R.id.country_name)

    fun bind(country: Country) {
        countryItem.text = country.name

        itemView.setOnClickListener {
            clickListener.onClick(country)
        }
    }
}
