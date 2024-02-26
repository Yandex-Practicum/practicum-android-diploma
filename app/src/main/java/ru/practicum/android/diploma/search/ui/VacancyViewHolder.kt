package ru.practicum.android.diploma.search.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.databinding.VacancyViewBinding

class VacancyViewHolder(
    private val binding: VacancyViewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(shortVacancy: ShortVacancy) {
        Glide.with(itemView)
            .load(shortVacancy.employerLogoUrl)
            .placeholder(R.drawable.placeholder_vacancy)
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.search_margin_s)))
            .centerInside()
            .into(binding.vacancyCover)

        val salaryFrom = shortVacancy.salaryFrom?.toIntOrNull() ?: 0
        val salaryTo = shortVacancy.salaryTo?.toIntOrNull() ?: 0

        binding.vacancyName.text = itemView.context.getString(R.string.vacancy_name, shortVacancy.name, shortVacancy.city)
        binding.companyName.text = itemView.context.getString(R.string.company_name, shortVacancy.companyName)
        binding.salary.text = itemView.context.getString(R.string.salary_format, salaryFrom, salaryTo)

    }
}
