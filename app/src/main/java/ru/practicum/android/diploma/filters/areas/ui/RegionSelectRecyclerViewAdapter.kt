package ru.practicum.android.diploma.filters.areas.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaCardBinding
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.ui.presenter.AreaViewHolder

class RegionSelectRecyclerViewAdapter(
    private val clickListener: RegionSelectClickListener
) : RecyclerView.Adapter<AreaViewHolder>() {

    var list = mutableListOf<Area>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val view = LayoutInflater.from(parent.context)
        return AreaViewHolder(AreaCardBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onClick(itemView)
        }
    }

    fun filterResults(request: String) {
        val filteredList = list.filter { area ->
            area.name
                .lowercase()
                .contains(request)
        }
        list.clear()
        list.addAll(filteredList)
        notifyDataSetChanged()
    }

    fun interface RegionSelectClickListener {
        fun onClick(area: Area)
    }
}
