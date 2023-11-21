package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Industry


class IndystryViewHolder(itemView: View, private val clickListener: (Industry) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.industry_region_title_textview)
    private val radioButton: RadioButton =
        itemView.findViewById(R.id.industry_region_check_radiobutton)
    private val rightArrow: ImageView = itemView.findViewById(R.id._region_check_radiobutton)
    fun bind(item: Industry) {
        name.text = item.name
        radioButton.visibility = View.VISIBLE
        rightArrow.visibility = View.GONE
        radioButton.isChecked = item.isChecked
        radioButton.setOnClickListener {
             if (radioButton!!.isChecked) {
                 clickListener.invoke(item)
             } else {
                 clickListener.invoke(item)
             }
        }

    }
}
