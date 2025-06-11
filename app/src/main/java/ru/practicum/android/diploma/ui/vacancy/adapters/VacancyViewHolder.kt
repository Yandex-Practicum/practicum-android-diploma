package ru.practicum.android.diploma.ui.vacancy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.util.pxToDp

class VacancyViewHolder private constructor(itemView: View) : ViewHolder(itemView) {
    private val imageEmployer: ImageView = itemView.findViewById(R.id.image_employer)
    private val nameVacancy: TextView = itemView.findViewById(R.id.text_name)
    private val nameEmployer: TextView = itemView.findViewById(R.id.text_employer)
    private val salary: TextView = itemView.findViewById(R.id.text_salary)

    constructor(
        parent: ViewGroup
    ) : this(
        LayoutInflater.from(parent.context).inflate(R.layout.vacancy_card, parent, false)
    )

    fun bind(vacancy: Vacancy) {
        nameVacancy.text = vacancy.name
        nameEmployer.text = vacancy.employerName
        if (vacancy.salaryFrom != null || vacancy.salaryTo != null) {
            var salaryText = ""
            if (vacancy.salaryFrom != null) {
                salaryText = "от ${vacancy.salaryFrom} "
            }
            if (vacancy.salaryTo != null) {
                salaryText += "до ${vacancy.salaryTo}"
            }
            salaryText += vacancy.salaryCurr
            salary.text = salaryText
            salary.isVisible = true
        } else {
            salary.isVisible = false
        }
        Glide.with(itemView.context)
            .load(vacancy.employerUrls)
            .placeholder(R.drawable.placeholder_32px)
            .transform(RoundedCorners(pxToDp(2f, itemView.context)))
            .into(imageEmployer)
    }
}
