package ru.practicum.android.diploma.ui.search.recycler

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.util.CurrencySymbol
import java.text.NumberFormat

class VacancyViewHolder(
    private val binding: VacancyViewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val numberFormat: NumberFormat by lazy {
        NumberFormat.getInstance()
    }


    fun bind(vacancy: Vacancy?, onClick: (Vacancy?) -> Unit) = with(binding) {
        tvVacancyName.text = vacancy?.name
        tvVacancyType.text = vacancy?.type?.name

        tvVacancySalary.text = makeSalaryString(vacancy)

        Glide.with(itemView)
            .load(vacancy?.employer?.logoUrls?.art90)
            .placeholder(R.drawable.logo_placeholder).error(R.drawable.logo_placeholder)
            .centerCrop().into(ivLogo)

        itemView.setOnClickListener {
            onClick.invoke(vacancy)
        }
    }

    private fun makeSalaryString(vacancy: Vacancy?): StringBuilder {
        val salaryStringBuilder = StringBuilder()

        if (vacancy?.salary?.from == null && vacancy?.salary?.to == null) {
            salaryStringBuilder.append(itemView.context.getString(R.string.salary_not_specified))
        } else {
            if (vacancy.salary.from != null) {
                salaryStringBuilder.append("${itemView.context.getString(R.string.from)} ${numberFormat.format(vacancy.salary.from)}")
            }
            if (vacancy.salary.to != null) {
                if (salaryStringBuilder.isNotEmpty()) {
                    salaryStringBuilder.append(" ")
                }
                salaryStringBuilder.append("${itemView.context.getString(R.string.to)} ${numberFormat.format(vacancy.salary.to)}")
            }

            if (vacancy.salary.currency != null && (vacancy.salary.from != null || vacancy.salary.to != null)) {
                salaryStringBuilder.append(" ${CurrencySymbol.get(vacancy.salary.currency)}")
            }
        }
        return salaryStringBuilder
    }
}
