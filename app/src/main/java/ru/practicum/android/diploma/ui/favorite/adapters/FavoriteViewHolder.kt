package ru.practicum.android.diploma.ui.favorite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.getCurrSymbol
import ru.practicum.android.diploma.util.pxToDp

class FavoriteViewHolder private constructor(itemView: View) : ViewHolder(itemView) {
    private val imageEmployer: ImageView = itemView.findViewById(R.id.artwork)
    private val nameVacancy: TextView = itemView.findViewById(R.id.title)
    private val nameEmployer: TextView = itemView.findViewById(R.id.employee_title)
    private val salary: TextView = itemView.findViewById(R.id.salary_title)

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context).inflate(R.layout.favorite_card, parent, false)
    )

    fun bind(vacancy: VacancyDetails) {
        nameVacancy.text = vacancy.vacancy.name
        nameEmployer.text = vacancy.vacancy.employerName
        salary.text = formatSalary(vacancy.vacancy.salaryFrom, vacancy.vacancy.salaryTo, vacancy.vacancy.salaryCurr)
        Glide.with(itemView.context)
            .load(vacancy.vacancy.employerUrls)
            .placeholder(R.drawable.placeholder_32px)
            .transform(RoundedCorners(pxToDp(2f, itemView.context)))
            .into(imageEmployer)
    }

    private fun formatSalary(salaryFrom: Int?, salaryTo: Int?, salaryCurr: String): String {
        if (salaryFrom == null && salaryTo == null) {
            return itemView.context.getString(R.string.salary_not_specified)
        }

        val formattedFrom = salaryFrom?.let { "%,d".format(it).replace(',', ' ') }
        val formattedTo = salaryTo?.let { "%,d".format(it).replace(',', ' ') }

        return when {
            formattedFrom != null && formattedTo != null ->
                itemView.context.getString(
                    R.string.salary_range,
                    formattedFrom,
                    formattedTo,
                    getCurrSymbol(itemView.context, salaryCurr)
                )

            formattedFrom != null ->
                itemView.context.getString(
                    R.string.salary_from,
                    formattedFrom,
                    getCurrSymbol(itemView.context, salaryCurr)
                )

            else ->
                itemView.context.getString(
                    R.string.salary_to,
                    formattedTo,
                    getCurrSymbol(itemView.context, salaryCurr)
                )
        }
    }
}
