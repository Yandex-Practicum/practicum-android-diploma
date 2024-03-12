package ru.practicum.android.diploma.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.FiltersItemBinding
import ru.practicum.android.diploma.domain.country.Country

class CountryAdapter : RecyclerView.Adapter<CountryViewHolder>() {

    val countryList = ArrayList<Country>()
    var itemClickListener: ((Int, Country) -> Unit)? = null
    var filteredList = ArrayList<Country>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CountryViewHolder(
            FiltersItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = filteredList[position]
        holder.bind(country)
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, country) }
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(text: String) {
        filteredList.clear()
        if (text.isEmpty()) {
            filteredList.addAll(countryList)
        } else {
            for (item in countryList) {
                if (item.name?.contains(text, ignoreCase = true) == true) {
                    filteredList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }
}
