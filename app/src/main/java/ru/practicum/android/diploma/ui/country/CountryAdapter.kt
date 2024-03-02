package ru.practicum.android.diploma.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.FiltersItemBinding

class CountryAdapter(private val recyclerItem: ArrayList<RecyclerItem>) : RecyclerView.Adapter<CountryViewHolder>() {

    var itemClickListener: ((Int, RecyclerItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CountryViewHolder(
            FiltersItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = recyclerItem[position]
        holder.bind(country)
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, country) }
    }

    override fun getItemCount(): Int = recyclerItem.size
}
