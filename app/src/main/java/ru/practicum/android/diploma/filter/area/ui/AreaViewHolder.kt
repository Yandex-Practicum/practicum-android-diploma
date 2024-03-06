package ru.practicum.android.diploma.filter.area.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.area.domain.model.Area

class AreaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.tvCountryOrRegion)

    fun bind(area: Area) {
        name.text = area.name
    }
}
