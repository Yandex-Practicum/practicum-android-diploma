package ru.practicum.android.diploma.ui.filter.workplace.region

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Region

class RegionsViewHolder(
    parent: ViewGroup,
    private val clickListener: RegionsAdapter.RegionClickListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.country_list_item, parent, false)
) {
    private val place = itemView.findViewById<TextView>(R.id.country_name)

    fun bind(region: Region) {
        place.text = region.name

        itemView.setOnClickListener {
            clickListener.onClick(region)
        }
    }
}
