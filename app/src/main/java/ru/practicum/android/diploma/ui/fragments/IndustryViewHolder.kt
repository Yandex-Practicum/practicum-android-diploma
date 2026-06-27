package ru.practicum.android.diploma.ui.fragments

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = itemView.findViewById(R.id.name)
    fun bind(model: Industry) {
        name.text = model.industryName
    }
}
