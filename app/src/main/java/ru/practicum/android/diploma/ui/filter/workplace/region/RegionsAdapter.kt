package ru.practicum.android.diploma.ui.filter.workplace.region

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Region

class RegionsAdapter(
    private val clickListener: RegionClickListener
) : RecyclerView.Adapter<RegionsViewHolder>() {

    private val regions = arrayListOf<Region>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RegionsViewHolder(parent, clickListener)

    override fun getItemCount() = regions.size
    override fun onBindViewHolder(holder: RegionsViewHolder, position: Int) {
        holder.bind(regions[position])
    }

    fun interface RegionClickListener {
        fun onClick(data: Region)
    }

    fun setData(data: List<Region>) {
        regions.clear()
        regions.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        regions.clear()
        notifyDataSetChanged()
    }
}
