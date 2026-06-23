package ru.practicum.android.diploma.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyCard

class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val vacancyName: TextView = itemView.findViewById(R.id.vacancyName)
    private val companyName: TextView = itemView.findViewById(R.id.companyName)
    private val salary: TextView = itemView.findViewById(R.id.salary)
    private val companyIcon: ImageView = itemView.findViewById(R.id.companyIcon)

    fun bind(item: VacancyCard) {
        vacancyName.text = "${item.vacancyName}, ${item.areaName}"
        companyName.text = item.companyName ?: "Не указано"

        var salaryText = ""

        if (item.salaryFrom != null) {
            salaryText += "от ${item.salaryFrom}"
        }

        if (item.salaryTo != null) {
            salaryText += " до ${item.salaryTo}"
        }

        if (item.currency != null) {
            salaryText += " ${item.currency}"
        }

        if (!salaryText.isEmpty() && (item.salaryFrom != null || item.salaryTo != null)) {
            salary.visibility = View.VISIBLE
            salary.text = salaryText
        }

        Glide.with(itemView.context)
            .load(item.shareUrl)
            .placeholder(R.drawable.icon_view_shape)
            .transform(CenterInside())
            .into(companyIcon)
    }
}
