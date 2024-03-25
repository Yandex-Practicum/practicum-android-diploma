package ru.practicum.android.diploma.ui.industries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.industries.ParentIndustriesAllDeal

class IndustriesAdapter() : RecyclerView.Adapter<IndustriesViewHolder>() {

    val industriesList = ArrayList<ParentIndustriesAllDeal>()
    var itemClickListener: ((Int, ParentIndustriesAllDeal) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IndustriesViewHolder(
            IndustryItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IndustriesViewHolder, position: Int) {
        val country = industriesList[position]
        holder.bind(country)
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, country) }
    }

    override fun getItemCount(): Int = industriesList.size
}
