package ru.practicum.android.diploma.filter.place.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.filter.databinding.ItemCountryBinding
import ru.practicum.android.diploma.filter.place.domain.model.Country

class CountriesAdapter(
    private val countryClickListener: CountryClickListener,
) : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    val countries: MutableList<Country> = ArrayList<Country>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CountriesViewHolder(ItemCountryBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.bind(countries[position])
        holder.itemView.setOnClickListener {
            countryClickListener.onCountryClick(countries[position].id)
        }
    }

    fun interface CountryClickListener {
        fun onCountryClick(id: String)
    }

    class CountriesViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.tvCountry.text = country.name
        }
    }
}
