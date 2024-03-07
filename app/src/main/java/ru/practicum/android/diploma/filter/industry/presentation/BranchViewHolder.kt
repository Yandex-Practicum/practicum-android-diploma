package ru.practicum.android.diploma.filter.industry.presentation

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.industry.domain.model.Industry

class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.lbl_btn_radio)
    private val rb: RadioButton = itemView.findViewById(R.id.btn_radio)

    fun bind(industry: Industry, isSelected: Boolean, onItemClick: (Industry) -> Unit) {
        name.text = industry.name
        rb.isChecked = isSelected
        itemView.setOnClickListener { onItemClick(industry) }
    }
}
