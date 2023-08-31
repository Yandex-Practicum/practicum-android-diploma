package ru.practicum.android.diploma.filters.ui.viewmodel

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filters.domain.models.FilterSelection
import ru.practicum.android.diploma.filters.ui.adapter.FilterSelectionClickListener

class
FiltersViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    private var textView:TextView = itemView.findViewById(R.id.SelectionFilters)

    fun bind( model: FilterSelection?, onClickListener: FilterSelectionClickListener? ){
        textView.text = model?.name
        itemView.setOnClickListener {
                onClickListener?.onClick(model!!)
        }
    }


}