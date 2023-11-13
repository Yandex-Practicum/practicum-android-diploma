package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Area

class AreaAdapter(
    private val items: List<Area>,
    private val clickListener: (Area) -> Unit
) : RecyclerView.Adapter<AreaViewHolder<Area>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder<Area> =
        AreaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.industry_area_item, parent, false),
            clickListener
        )

    override fun onBindViewHolder(holder: AreaViewHolder<Area>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
