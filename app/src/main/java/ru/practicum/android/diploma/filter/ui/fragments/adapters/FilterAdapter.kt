package ru.practicum.android.diploma.filter.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.databinding.ItemCountryFilterBinding
import ru.practicum.android.diploma.databinding.ItemRegionDepartFilterBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class FilterAdapter @Inject constructor(
    private val logger: Logger,
    private val debouncer: Debouncer,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var holderKind: String = ""
    var onClickCountry: ((Country) -> Unit)? = null
    var onClickRegion: ((Region) -> Unit)? = null

    var countryList = listOf<Country>()
    var regionList = listOf<Region>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (holderKind) {
            "Country" -> {
                CountryViewHolder(ItemCountryFilterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)
                )
            }
            "Region" -> {
                RegionViewHolder(ItemRegionDepartFilterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)
                )
            }
            else -> throw Exception("$thisName -> Wrong ViewHolder")
        }
    }

    override fun getItemCount() = countryList.size + regionList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holderKind == "Country") {
            val pos = holder.adapterPosition
            val item = countryList[pos]
            holder as CountryViewHolder
            holder.bind(item)
            holder.itemView.debounceClickListener(debouncer) {
                logger.log("FilterAdapter", "onClickCountry")
                onClickCountry?.invoke(item)
            }
        }
        if (holderKind == "Region") {
            val pos = holder.adapterPosition
            val item = regionList[pos]
            holder as RegionViewHolder
            holder.bind(item)
            holder.itemView.debounceClickListener(debouncer) {
                logger.log("FilterAdapter", "onClickRegion")
                onClickRegion?.invoke(item)
            }
        }
    }
}