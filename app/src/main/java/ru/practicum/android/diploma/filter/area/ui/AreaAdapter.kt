package ru.practicum.android.diploma.filter.area.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.area.domain.model.Area

class AreaAdapter(private val clickListener: (area: Area) -> Unit) :
    RecyclerView.Adapter<AreaViewHolder>() {
    val areas = ArrayList<Area>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.text_button_item, parent, false)
        return AreaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.bind(areas[position])
        holder.itemView.setOnClickListener { clickListener.invoke(areas[position]) }
    }

    override fun getItemCount(): Int {
        return areas.size
    }
}
