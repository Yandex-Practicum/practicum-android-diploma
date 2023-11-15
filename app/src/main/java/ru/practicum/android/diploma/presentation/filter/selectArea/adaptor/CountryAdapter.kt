package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Country


class CountryAdapter(
    private val items: List<Country>,
    private val clickListener: (Country) -> Unit
) : RecyclerView.Adapter<CountryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.country_item, parent, false)
        )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}