package ru.practicum.android.diploma.ui.filter.workplace.country.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.RegionCellViewBinding
import ru.practicum.android.diploma.domain.country.Country

class CountryAdapter : RecyclerView.Adapter<CountryViewHolder>() {

    val countryList = ArrayList<Country>()
    var itemClickListener: ((Int, Country) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CountryViewHolder(
            RegionCellViewBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countryList[position]
        holder.bind(country)
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, country) }
    }

    override fun getItemCount(): Int = countryList.size
}
