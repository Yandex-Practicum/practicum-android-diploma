package ru.practicum.android.diploma.ui.industries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.data.response.Industries
import ru.practicum.android.diploma.databinding.IndustryItemBinding

class IndustriesAdapter(private val industries: ArrayList<Industries>) : RecyclerView.Adapter<IndustriesViewHolder>() {

    var itemClickListener: ((Int, Industries) -> Unit)? = null

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
        val country = industries[position]
        holder.bind(country)
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, country) }
    }

    override fun getItemCount(): Int = industries.size
}
