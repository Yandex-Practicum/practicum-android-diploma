package ru.practicum.android.diploma.filters.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filters.domain.models.Industries
import ru.practicum.android.diploma.filters.domain.models.Region
import ru.practicum.android.diploma.filters.ui.viewholder.ViewHolder

class FiltersAdapter(val onClickListenre:FilterSelectionClickListener ):RecyclerView.Adapter<ViewHolder>() {

    var regionList = mutableListOf<Region>()
    var industriesList = mutableListOf<Industries>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.choose_region_and_industry_view, parent,
                    false
                )
            )
    }
    override fun getItemCount(): Int {
        if(regionList.isEmpty()){
            return industriesList.size
        }else{
            return regionList.size
        }

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(regionList.isEmpty()) {
            holder.bindIndustries(industriesList.get(position), onClickListenre)
        }else{
            holder.bindRegion(regionList.get(position), onClickListenre)
        }
    }
    fun setRegion(newRegionList:List<Region>){
        industriesList.clear()
        regionList.addAll(newRegionList)
        notifyDataSetChanged()
    }
    fun setIndustrie(newIndustriesList:List<Industries>){
        regionList.clear()
        industriesList.addAll(newIndustriesList)
        notifyDataSetChanged()
    }
}

 interface FilterSelectionClickListener {
    fun onClickRegion(model:Region)
    fun onClickIndustries(model:Industries)
}