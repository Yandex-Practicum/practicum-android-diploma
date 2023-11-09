package ru.practicum.android.diploma.presentation.filter.chooseArea.adaptor

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.IndustryAreaModel


class AreasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.industry_region_title_textview)
    private val radioButton: RadioButton =
        itemView.findViewById(R.id.industry_region_check_radiobutton)

    fun bind( item: IndustryAreaModel) {
        name.text = item.name
    }
}