package ru.practicum.android.diploma.filter.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.databinding.ItemCountryFilterBinding
import ru.practicum.android.diploma.databinding.ItemRegionDepartFilterBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.fragments.CountryFragment.Companion.COUNTRY
import ru.practicum.android.diploma.filter.ui.fragments.DepartmentFragment.Companion.DEPARTMENT
import ru.practicum.android.diploma.filter.ui.fragments.RegionFragment.Companion.REGION
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class FilterAdapter @Inject constructor(
    private val logger: Logger,
    private val debouncer: Debouncer,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var fragment: String = ""
    var onClickCountry: ((Country) -> Unit)? = null
    var onClickRegion: ((Region) -> Unit)? = null
    var onClickIndustry: ((Industry) -> Unit)? = null

    var countryList = listOf<Country>()
    var regionList = listOf<Region>()
    var industryList = listOf<Industry>()

    private var isNewRadioButtonChecked = false
    private var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (fragment) {
            COUNTRY -> {
                CountryViewHolder(
                    ItemCountryFilterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            REGION -> {
                RegionViewHolder(
                    ItemRegionDepartFilterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            DEPARTMENT -> {
                IndustryViewHolder(
                    ItemRegionDepartFilterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> throw Exception("$thisName -> Wrong ViewHolder")
        }
    }

    override fun getItemCount() = countryList.size + regionList.size + industryList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        if (fragment == COUNTRY) {
            val item = countryList[pos]
            holder as CountryViewHolder
            holder.bind(item)
            holder.itemView.debounceClickListener(debouncer) {
                logger.log("FilterAdapter", "onClickCountry: country = $item")
                onClickCountry?.invoke(item)
            }
        }
        if (fragment == REGION) {
            val item = regionList[pos]
            holder as RegionViewHolder
            holder.bind(item)
            holder.itemView.debounceClickListener(debouncer) {
                logger.log("FilterAdapter", "onClickRegion: region = $item")
                onClickRegion?.invoke(item)
            }
        }
        if (fragment == DEPARTMENT) {
            val item = industryList[pos]
            holder as IndustryViewHolder
            holder.bind(item)
            holder.itemView.debounceClickListener(debouncer) {
                logger.log("FilterAdapter", "onClickIndustry: industry = $item")
                onClickIndustry?.invoke(item)
            }
        }
    }
}