package ru.practicum.android.diploma.ui.filter.place.region.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.ui.filter.place.models.Region

class RegionsAdapter(private val clickListener: RegionClickListener) : RecyclerView.Adapter<RegionsViewHolder>() {
    val regions: MutableList<Region> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RegionsViewHolder {
        return RegionsViewHolder(parent)
    }

    override fun onBindViewHolder(
        holder: RegionsViewHolder,
        position: Int
    ) {
        val region = regions[position]
        holder.itemView.setOnClickListener { clickListener.onCountryClick(region) }
        holder.bind(region)
    }

    override fun getItemCount(): Int {
        return regions.size
    }

    fun interface RegionClickListener {
        fun onCountryClick(region: Region)
    }
}
