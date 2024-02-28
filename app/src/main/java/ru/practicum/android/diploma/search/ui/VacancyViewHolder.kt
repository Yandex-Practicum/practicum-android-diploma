package ru.practicum.android.diploma.search.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.util.CurrencySymbol
import ru.practicum.android.diploma.vacancy.ui.VacancyFragment

class VacancyViewHolder(
    private val binding: VacancyViewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(shortVacancy: ShortVacancy, onItemClickListener: ((ShortVacancy) -> Unit)?) {
        Glide.with(itemView)
            .load(shortVacancy.employerLogoUrl)
            .placeholder(R.drawable.placeholder_vacancy)
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.search_margin_s)))
            .centerInside()
            .into(binding.vacancyCover)

        val salaryFrom = shortVacancy.salaryFrom.toIntOrNull() ?: 0
        val salaryTo = shortVacancy.salaryTo.toIntOrNull() ?: 0
        itemView.setOnClickListener { onItemClickListener?.invoke(shortVacancy) }

        binding.vacancyName.text = if (shortVacancy.city.isEmpty()) {
            shortVacancy.name
        } else {
            itemView.context.getString(R.string.vacancy_name, shortVacancy.name, shortVacancy.city)
        }

        binding.companyName.text = itemView.context.getString(R.string.company_name, shortVacancy.companyName)

        binding.salary.text = (if (salaryFrom == 0 && salaryTo == 0) {
            itemView.context.getString(R.string.tv_salary_no_info)
        } else if (salaryTo == 0) {
            itemView.context.getString(
                R.string.tv_salary_from_info,
                shortVacancy.salaryFrom,
                CurrencySymbol.getCurrencySymbol(shortVacancy.currency)
            )
        } else if (salaryFrom == 0) {
            itemView.context.getString(
                R.string.tv_salary_to_info,
                shortVacancy.salaryTo,
                CurrencySymbol.getCurrencySymbol(shortVacancy.currency)
            )
        } else {
            itemView.context.getString(
                R.string.tv_salary_from_to_info,
                shortVacancy.salaryFrom,
                shortVacancy.salaryTo,
                CurrencySymbol.getCurrencySymbol(shortVacancy.currency)
            )
        }).toString()

    }
}
