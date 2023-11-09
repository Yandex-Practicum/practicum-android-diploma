package ru.practicum.android.diploma.presentation.filter.chooseArea.adaptor

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Country

class CountriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.country_item_textview)

    fun bind(country: Country) {
        name.text = country.name
    }
}