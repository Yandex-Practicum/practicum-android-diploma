package ru.practicum.android.diploma.util.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.models.Vacancy


class VacancyViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.item_view, parentView, false)
) {
    private val logoUrl: ImageView = itemView.findViewById(R.id.logo_url)
    private val name: TextView = itemView.findViewById(R.id.vacancy_name)
    private val employerName: TextView = itemView.findViewById(R.id.employer_name)
    private val salary: TextView = itemView.findViewById(R.id.salary)

    fun bind(model: Vacancy) {
        name.text = "${model.name}, ${model.city}"
        employerName.text = model.employerName
        salary.text = getSalary(model, salary.context)
        Glide.with(itemView)
            .load(model.employerLogoUrl)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .into(logoUrl)
        if (model.employerLogoUrl != null) logoUrl.setPadding(3, 3, 3, 3)
    }

    private fun getSalary(model: Vacancy, context: Context): String {
        return when {
            (model.salaryCurrency == null) ->
                context.getString(R.string.no_salary)

            (model.salaryTo == null && model.salaryFrom == null) ->
                context.getString(R.string.no_salary)

            (model.salaryTo == null) ->
                "${context.getString(R.string.from)} ${model.salaryFrom} ${model.salaryCurrency}"

            (model.salaryFrom == null) ->
                "${context.getString(R.string.to)} ${model.salaryTo} ${model.salaryCurrency}"

            else ->
                "${context.getString(R.string.from)} ${model.salaryFrom} " +
                        "${context.getString(R.string.to)} ${model.salaryTo} ${model.salaryCurrency}"
        }
    }
}