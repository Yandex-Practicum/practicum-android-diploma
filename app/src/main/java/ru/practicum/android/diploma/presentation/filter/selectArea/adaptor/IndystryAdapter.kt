package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Industry

class IndystryAdapter(
    private var items: List<Industry>,
    private val clickListener: (Industry) -> Unit
) : RecyclerView.Adapter<IndystryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndystryViewHolder =
        IndystryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.industry_area_item, parent, false),
            clickListener
        )

    override fun onBindViewHolder(holder: IndystryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}

