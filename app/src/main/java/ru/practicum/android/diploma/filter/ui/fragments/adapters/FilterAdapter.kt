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

    private var selectedPosition = -1

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
        val isSelected = selectedPosition == pos
        when (fragment) {
            COUNTRY -> {
                val item = countryList[pos]
                holder as CountryViewHolder
                holder.bind(item)
                holder.itemView.debounceClickListener(debouncer) {
                    onClickCountry?.invoke(item)
                    logger.log(thisName, "onClickCountry?.invoke($item)")
                }
            }
            REGION -> {
                val item = regionList[pos]
                holder as RegionViewHolder
                holder.bind(item, state = isSelected)
                holder.itemView.debounceClickListener(debouncer) {
                    onItemPressed(holder, pos, selectedPosition)
                    onClickRegion?.invoke(item)
                    logger.log(thisName, "onClickRegion?.invoke($item)")
                }
            }
            DEPARTMENT -> {
                val item = industryList[pos]
                holder as IndustryViewHolder
                holder.bind(item)
                holder.itemView.debounceClickListener(debouncer) {
                    onItemPressed(holder, pos, selectedPosition)
                    onClickIndustry?.invoke(item)
                    logger.log(thisName, "onClickIndustry?.invoke($item)")
                }
            }
        }
    }

    private fun onItemPressed(holder: RecyclerView.ViewHolder, currentPos: Int, prev: Int) {
        val previousPos = if (prev == -1) 0 else prev
        val itemPrevPos = regionList[previousPos]
        val item = regionList[currentPos]

        when (fragment) {
            REGION -> {
                holder as RegionViewHolder
                holder.bind(itemPrevPos, state = false)
                holder.bind(item, state = true)
            }
            DEPARTMENT -> {
                holder as IndustryViewHolder
//                holder.bind(itemPrevPos, state = false)
//                holder.bind(item, state = true)
            }
        }
        notifyItemChanged(previousPos)
        selectedPosition = currentPos
    }
}