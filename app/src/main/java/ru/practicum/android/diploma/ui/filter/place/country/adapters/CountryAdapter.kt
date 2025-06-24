package ru.practicum.android.diploma.ui.filter.place.country.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.ui.filter.place.models.Country

class CountryAdapter(private val clickListener: CountryClickListener) : RecyclerView.Adapter<CountryViewHolder>() {
    val countries: MutableList<Country> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        return CountryViewHolder(parent)
    }

    override fun onBindViewHolder(
        holder: CountryViewHolder,
        position: Int
    ) {
        holder.bind(countries[position])
        holder.itemView.setOnClickListener { clickListener.onCountryClick(countries[position]) }
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    fun interface CountryClickListener {
        fun onCountryClick(country: Country)
    }
}
