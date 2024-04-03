package ru.practicum.android.diploma.ui.filter.workplace.region.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.RegionCellViewBinding
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.ui.filter.workplace.country.adapter.RegionViewHolder

class RegionAdapter : RecyclerView.Adapter<RegionViewHolder>() {

    val regionList = ArrayList<Country>()
    var itemClickListener: ((Int, Country) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RegionViewHolder(
            RegionCellViewBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val country = regionList[position]
        holder.bind(country)
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, country) }
    }

    override fun getItemCount(): Int = regionList.size
}
