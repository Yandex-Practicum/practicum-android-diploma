package ru.practicum.android.diploma.filters.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filters.domain.models.Industries
import ru.practicum.android.diploma.filters.domain.models.Region
import ru.practicum.android.diploma.filters.ui.adapter.FilterSelectionClickListener

class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    private var textView:TextView = itemView.findViewById(R.id.SelectionFilters)

    fun bindRegion(model: Region?, onClickListener: FilterSelectionClickListener? ){
        textView.text = model?.name
        itemView.setOnClickListener {
                onClickListener?.onClickRegion(model!!)
        }
    }
    fun bindIndustries(model: Industries?, onClickListener: FilterSelectionClickListener? ){
        textView.text = model?.name
        itemView.setOnClickListener {
            onClickListener?.onClickIndustries(model!!)
        }
    }


}