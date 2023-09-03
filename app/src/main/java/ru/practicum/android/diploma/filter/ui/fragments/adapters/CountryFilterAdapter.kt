package ru.practicum.android.diploma.filter.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.CountryFilterItemBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import javax.inject.Inject

class CountryFilterAdapter @Inject constructor() :
    RecyclerView.Adapter<CountryFilterAdapter.CountryViewHolder>() {
    var onItemClick: ((Country) -> Unit)? = null
    var countriesList: List<Country> = ArrayList<Country>()

    class CountryViewHolder(private val binding: CountryFilterItemBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        fun bind(model: Country) {
            binding.countryName.text = model.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(
            CountryFilterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = countriesList.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.bind(countriesList[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(countriesList[position])
        }

    }
}