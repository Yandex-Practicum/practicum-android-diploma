package ru.practicum.android.diploma.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.R

class IndustryAdapter(private val onItemClick: (Industry) -> Unit) :
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
            val industry = industrys[position]
            holder.bind(industry)
            holder.itemView.setOnClickListener { onItemClick(industry) }     }

        override fun getItemCount(): Int {
            return industrys.size
        }
}

