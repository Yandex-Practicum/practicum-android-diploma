package ru.practicum.android.diploma.presentation.filter.chooseArea.adaptor

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Country


class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.industry_region_title_textview)

    fun bind(item: Country) {
        name.text = item.name
    }
}