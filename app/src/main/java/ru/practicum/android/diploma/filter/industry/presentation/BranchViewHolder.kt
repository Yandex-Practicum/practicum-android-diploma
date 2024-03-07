package ru.practicum.android.diploma.filter.industry.presentation

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.industry.domain.model.Industry

class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.lbl_btn_radio)
    val rb: RadioButton = itemView.findViewById(R.id.btn_radio)

    fun bind(branch: Industry) {

    }
}
