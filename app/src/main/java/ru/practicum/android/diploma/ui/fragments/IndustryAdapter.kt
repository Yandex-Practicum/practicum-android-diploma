package ru.practicum.android.diploma.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.data.db.Industry
import ru.practicum.android.diploma.R

class IndustryAdapter() :
        RecyclerView.Adapter<IndustryViewHolder>() {
        var industrys = listOf<Industry>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IndustryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_industry_view, parent, false)
        return IndustryViewHolder(view)
    }

        override fun onBindViewHolder(
            holder: IndustryViewHolder,
            position: Int
        ) {
            holder.bind(industrys.get(position))
//            holder.itemView.setOnClickListener { clickListener.onTrackClick(tracks.get(position)) }
        }

        override fun getItemCount(): Int {
            return industrys.size
        }
}

