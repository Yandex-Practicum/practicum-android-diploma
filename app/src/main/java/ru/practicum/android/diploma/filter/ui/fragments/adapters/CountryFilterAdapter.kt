package ru.practicum.android.diploma.filter.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.databinding.ItemCountryFilterBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.debounceClickListener
import javax.inject.Inject

class CountryFilterAdapter @Inject constructor(
    private val logger: Logger,
    private val debouncer: Debouncer,
) :
    RecyclerView.Adapter<CountryFilterAdapter.CountryViewHolder>() {
    var onItemClick: ((Country) -> Unit)? = null
    var countriesList: List<Country> = ArrayList<Country>()

    class CountryViewHolder(private val binding: ItemCountryFilterBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        fun bind(model: Country) {
            binding.countryName.text = model.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(
            ItemCountryFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = countriesList.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.bind(countriesList[position])
        holder.itemView.debounceClickListener(debouncer) {
            logger.log("Adapter", "onItemClick()")
            onItemClick?.invoke(countriesList[position])
        }
    }
}