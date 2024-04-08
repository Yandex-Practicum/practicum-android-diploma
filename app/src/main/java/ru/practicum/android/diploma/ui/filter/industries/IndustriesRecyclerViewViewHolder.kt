package ru.practicum.android.diploma.ui.filter.industries

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class IndustriesRecyclerViewViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private val industryName: TextView = itemView.findViewById(R.id.industry_name)
    private val industryRadioButton: RadioButton = itemView.findViewById(R.id.industry_radio_button)

    fun bind(model: ChildIndustryWithSelection) {
        industryName.text = model.name
        industryRadioButton.isChecked = model.selected
    }
}
