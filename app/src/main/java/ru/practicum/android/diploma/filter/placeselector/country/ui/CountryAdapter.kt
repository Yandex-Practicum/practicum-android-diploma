package ru.practicum.android.diploma.filter.placeselector.country.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.Country

class CountryAdapter(private val clickListener: (country: Country) -> Unit) :
    RecyclerView.Adapter<CountryViewHolder>() {
    val countries = ArrayList<Country>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.text_button_item, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
        holder.itemView.setOnClickListener { clickListener.invoke(countries[position]) }
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}
