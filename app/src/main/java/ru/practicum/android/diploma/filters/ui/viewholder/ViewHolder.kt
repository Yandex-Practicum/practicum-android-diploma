package ru.practicum.android.diploma.filters.ui.viewholder

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filters.domain.models.Country
import ru.practicum.android.diploma.filters.domain.models.Industries
import ru.practicum.android.diploma.filters.domain.models.Region
import ru.practicum.android.diploma.filters.ui.adapter.FilterSelectionClickListener

class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    private var textView: TextView = itemView.findViewById(R.id.SelectionFilters)
    private var checkBox: CheckBox? = itemView.findViewById(R.id.filter_checkbox)
    private var rightArrow:ImageView? = itemView.findViewById(R.id.choose_country_bottom)



    fun bindRegion(model: Region?, onClickListener: FilterSelectionClickListener?) {
        textView.text = model?.name
        rightArrow?.visibility = View.GONE
        checkBox?.visibility = View.VISIBLE
        checkBox?.setOnClickListener {
            if (checkBox!!.isChecked) {
                onClickListener?.onClickRegion(model!!)
            }else{
                onClickListener?.onClickRegion(null)
            }
        }
    }
    fun bindIndustries(model: Industries?, onClickListener: FilterSelectionClickListener?) {
        textView.text = model?.name
        rightArrow?.visibility = View.GONE
        checkBox?.visibility = View.VISIBLE
        checkBox?.setOnClickListener {
            if (checkBox!!.isChecked) {
                onClickListener?.onClickIndustries(model!!)
            }else{
                onClickListener?.onClickIndustries(null)
            }
        }
    }
    fun bindCountry(model: Country?, onClickListener: FilterSelectionClickListener?){
        textView.text = model?.name
        checkBox?.visibility = View.GONE
        rightArrow?.setOnClickListener {
            onClickListener?.onClickCountry(model)
        }


    }
}