package ru.practicum.android.diploma.ui.filter.industries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class IndustriesFragmentRecyclerViewAdapter(
    private val industries: List<ChildIndustryWithSelection>
) : RecyclerView.Adapter<IndustriesFragmentRecyclerViewViewHolder>() {

    //var industryClicked: (ChildIndustryWithSelection) -> Unit = {}
    var industryNumberClicked: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesFragmentRecyclerViewViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.industries_view,
                parent,
                false
            )
        return IndustriesFragmentRecyclerViewViewHolder(view) //, industryClicked)
    }

    override fun onBindViewHolder(holder: IndustriesFragmentRecyclerViewViewHolder, position: Int) {
        holder.bind(industries[position])
        holder.itemView.setOnClickListener {
            industryNumberClicked(position)
            //Log.d("CLICKED", "at item#${ position }")
        }
    }

    override fun getItemCount(): Int = industries.size
}
