package ru.practicum.android.diploma.filters.areas.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryCardBinding
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.filters.industries.ui.presenter.IndustriesViewHolder
import ru.practicum.android.diploma.filters.ui.presenter.AreasViewHolder
import ru.practicum.android.diploma.util.network.RegionInformation

class RegionSelectRecyclerViewAdapter(
    private val clickListener: RegionSelectClickListener
) : RecyclerView.Adapter<IndustriesViewHolder>() {

    var list = mutableListOf<Industry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesViewHolder {
        val view = LayoutInflater.from(parent.context)
        return IndustriesViewHolder(IndustryCardBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AreasViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onClick(itemView)
        }
    }

    fun interface RegionSelectClickListener {
        fun onClick(regionInformation: RegionInformation)
    }
}
