package ru.practicum.android.diploma.ui.vacancy

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.VacanciesPreview
import ru.practicum.android.diploma.util.dpToPx
import ru.practicum.android.diploma.util.formatWithThousandsSeparator

class VacanciesViewHolder(
    private val binding: VacancyItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: VacanciesPreview) {
        val context = itemView.context
        Glide.with(context)
            .load(item.employerLogo)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(CORNERS, context)))
            .placeholder(R.drawable.employer_logo_placeholder)
            .into(binding.logo)

        binding.name.text =
            context.getString(
                R.string.vacancy_name_area,
                item.vacancyName,
                item.address.substringBefore(",")
            )

        binding.employer.text = item.employerName

        binding.salary.text = StringBuilder().apply {
            if (item.salaryFrom != null || item.salaryTo != null) {
                if (item.salaryFrom != null) {
                    append(context.getString(R.string.from))
                    append(" ")
                    append(item.salaryFrom.formatWithThousandsSeparator())
                }
                if (item.salaryTo != null) {
                    append(" ")
                    append(context.getString(R.string.to))
                    append(" ")
                    append(item.salaryTo.formatWithThousandsSeparator())
                }
                append(" ")
                append(item.currency)
            } else {
                append(context.getString(R.string.salary_not_specified))
            }
        }.toString()
    }

    companion object {
        const val CORNERS = 12f
    }
}
