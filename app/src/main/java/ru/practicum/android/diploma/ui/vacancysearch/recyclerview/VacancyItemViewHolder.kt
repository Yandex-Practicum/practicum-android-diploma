package ru.practicum.android.diploma.ui.vacancysearch.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel
import ru.practicum.android.diploma.util.dpToPx

class VacancyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val coverVacancy = itemView.findViewById<ImageView>(R.id.cover_vacancy)
    private val nameVacancy = itemView.findViewById<TextView>(R.id.name_vacancy)
    private val employeeName = itemView.findViewById<TextView>(R.id.employee_name)
    private val salaryVacancy = itemView.findViewById<TextView>(R.id.salary_vacancy)

    fun bind(vacancy: VacancyUiModel) {
        nameVacancy.text = vacancy.nameVacancy
        employeeName.text = vacancy.employerName
        salaryVacancy.text = vacancy.salary
        vacancy.logo?.let { loadImage(it) }
    }

    private fun loadImage(url: String) {
        val cornerRadius = dpToPx(12f, itemView.context)

        Glide.with(itemView)
            .load(url)
            .transform(RoundedCorners(cornerRadius))
            .placeholder(R.drawable.vacancy_placeholder)
            .into(coverVacancy)
    }
}
