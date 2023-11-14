package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Area

class AreaViewHolder<T : Area>(itemView: View, private val clickListener: (T) -> Unit) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.industry_region_title_textview)
    private val radioButton: RadioButton =
        itemView.findViewById(R.id.industry_region_check_radiobutton)

    fun bind(item: T) {
        name.text = item.name
        itemView.setOnClickListener {
            clickListener.invoke(item)
        }
    }
}