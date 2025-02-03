package ru.practicum.android.diploma.filter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryRegionBinding
import ru.practicum.android.diploma.filter.domain.model.Country

class CountryAdapter(
    private val listener: CountryClickListener,
) : RecyclerView.Adapter<CountryViewHolder>() {

    private val countries = ArrayList<Country>()
    private var selectedIndex: Int? = null

    fun setCountries(newCountries: List<Country>) {
        selectedIndex = null
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged() // Уведомляем адаптер об изменениях
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CountryViewHolder(
            ItemCountryRegionBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val item = countries[position]

        holder.bind(item)
        holder.binding.buttonImage.setOnClickListener {
            listener.onCountryClick(item)
        }

        holder.binding.countryOrRegion.setOnClickListener {
            listener.onCountryClick(item)
        }

    }

    fun interface CountryClickListener {
        fun onCountryClick(item: Country)
    }
}
