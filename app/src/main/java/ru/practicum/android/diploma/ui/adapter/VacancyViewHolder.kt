package ru.practicum.android.diploma.ui.adapter

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
        } else {
            salary.text = "Зарплата не указана"
            salary.visibility = View.VISIBLE
        }

        Glide.with(itemView.context)
            .load(item.logoUrl)
            .placeholder(R.drawable.ic_placeholder_32)
            .centerInside()
            .transform(CenterInside(),RoundedCorners(dpToPx(12f, itemView.context)))
            .into(companyIcon)
    }
    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}
