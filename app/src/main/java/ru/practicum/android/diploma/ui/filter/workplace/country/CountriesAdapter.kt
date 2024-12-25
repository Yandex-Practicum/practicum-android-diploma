package ru.practicum.android.diploma.ui.filter.workplace.country

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Country

class CountriesAdapter(
    private val clickListener: CountryClickListener
) : RecyclerView.Adapter<CountriesViewHolder>() {

    private val countries = arrayListOf<Country>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountriesViewHolder(parent, clickListener)

    override fun getItemCount() = countries.size
    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    fun interface CountryClickListener {
        fun onClick(data: Country)
    }

    fun setData(data: List<Country>) {
        countries.clear()
        countries.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        countries.clear()
        notifyDataSetChanged()
    }
}
