package ru.practicum.android.diploma.filters.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filters.domain.models.FilterSelection
import ru.practicum.android.diploma.filters.ui.viewmodel.FiltersViewHolder

class FiltersAdapter(val onClickListenre:FilterSelectionClickListener ):RecyclerView.Adapter<FiltersViewHolder>() {

    var filterSelection = mutableListOf<FilterSelection>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        return FiltersViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.choose_region_and_industry_view, parent,
            false
        ))
    }
    override fun getItemCount(): Int {
        return filterSelection.size
    }
    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {
        holder.bind(filterSelection.get(position), onClickListenre)
    }
}
 interface FilterSelectionClickListener {
    fun onClick(model:FilterSelection)

}