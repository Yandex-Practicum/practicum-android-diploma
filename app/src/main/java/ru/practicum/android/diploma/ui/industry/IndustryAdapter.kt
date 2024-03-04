package ru.practicum.android.diploma.ui.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.FiltersItemBinding
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.ui.country.CountryViewHolder
import ru.practicum.android.diploma.ui.country.RecyclerItem

class IndustryAdapter (private val recyclerItem: ArrayList<RecyclerItem>) : RecyclerView.Adapter<IndustryViewHolder>() {

    var itemClickListener: ((Int, RecyclerItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IndustryViewHolder(
            IndustryItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val country = recyclerItem[position]
        holder.bind(country)
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, country) }
    }

    override fun getItemCount(): Int = recyclerItem.size
}
