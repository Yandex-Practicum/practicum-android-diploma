package ru.practicum.android.diploma.search.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.util.StringUtils

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

        itemView.setOnClickListener { onItemClickListener?.invoke(shortVacancy) }
        binding.vacancyName.text = StringUtils.getVacancyTitle(shortVacancy.name, shortVacancy.city, itemView.context)
        binding.companyName.text = itemView.context.getString(R.string.company_name, shortVacancy.companyName)
        binding.salary.text = StringUtils.getSalary(
            salaryFrom = shortVacancy.salaryFrom,
            salaryTo = shortVacancy.salaryTo,
            currency = shortVacancy.currency,
            context = itemView.context
        )
    }
}
