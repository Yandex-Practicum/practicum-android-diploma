package ru.practicum.android.diploma.ui.filter.industries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class IndustriesRecyclerViewAdapter(
    private val industries: List<ChildIndustryWithSelection>
) : RecyclerView.Adapter<IndustriesRecyclerViewViewHolder>() {
    var industryNumberClicked: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesRecyclerViewViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.industries_view,
                parent,
                false
            )
        return IndustriesRecyclerViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndustriesRecyclerViewViewHolder, position: Int) {
        holder.bind(industries[position])
        holder.itemView.setOnClickListener {
            industryNumberClicked(position)
        }
    }

    override fun getItemCount(): Int = industries.size
}
