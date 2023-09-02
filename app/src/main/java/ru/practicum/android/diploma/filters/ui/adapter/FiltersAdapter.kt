package ru.practicum.android.diploma.filters.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filters.domain.models.Country
import ru.practicum.android.diploma.filters.domain.models.Industries
import ru.practicum.android.diploma.filters.domain.models.Region
import ru.practicum.android.diploma.filters.ui.viewholder.ViewHolder

class FiltersAdapter(val onClickListener:FilterSelectionClickListener):RecyclerView.Adapter<ViewHolder>() {

    var regionList = mutableListOf<Region>()
    var industriesList = mutableListOf<Industries>()
    var countryList = mutableListOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.filter_selection_itemview, parent,
                    false
                )
            )
    }
    override fun getItemCount(): Int {
        return regionList.size+industriesList.size+countryList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        regionList.takeIf { it.isNotEmpty()}?.let {
            holder.bindRegion(regionList.get(position), onClickListener)
        }
        industriesList.takeIf { it.isNotEmpty()}?.let {
            holder.bindIndustries(industriesList.get(position), onClickListener)
        }
        countryList.takeIf { it.isNotEmpty()}?.let {
            holder.bindCountry(countryList.get(position), onClickListener)
        }
    }
    fun setRegion(newRegionList:List<Region>){
        industriesList.clear()
        countryList.clear()
        regionList.addAll(newRegionList)
        notifyDataSetChanged()
    }
    fun setIndustrie(newIndustriesList:List<Industries>){
        regionList.clear()
        countryList.clear()
        industriesList.addAll(newIndustriesList)
        notifyDataSetChanged()
    }
    fun setCountry(newCountryList:List<Country>){
        regionList.clear()
        industriesList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}
 interface FilterSelectionClickListener {
    fun onClickRegion(model:Region?)
    fun onClickIndustries(model:Industries?)
    fun onClickCountry(model:Country?)
}