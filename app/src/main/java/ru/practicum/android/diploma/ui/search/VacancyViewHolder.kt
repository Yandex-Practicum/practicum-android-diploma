package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class VacancyViewHolder(
    private val binding: VacancyViewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy, onClick: (Vacancy) -> Unit) = with(binding) {
        tvVacancyName.text = vacancy.name
        tvVacancyType.text = vacancy.type.name
        if (vacancy.salary?.to != null) {
            tvVacancySalary.text = "${itemView.context.getString(R.string.to)} ${vacancy.salary.to}"
        } else if (vacancy.salary?.from != null) {
            tvVacancySalary.text = "${itemView.context.getString(R.string.from)} ${vacancy.salary.from}"
        } else if (vacancy.salary?.gross != null) {
            tvVacancySalary.text = "${itemView.context.getString(R.string.gross)} ${vacancy.salary.to}"
        } else if (vacancy.salary?.currency != null) {
            tvVacancySalary.text = vacancy.salary.currency.toString()
        }


        Glide.with(itemView)
            .load(vacancy.employer?.logoUrls?.art90)
            .placeholder(R.drawable.logo_placeholder).error(R.drawable.logo_placeholder)
            .centerCrop().into(ivLogo)

        itemView.setOnClickListener {
            onClick.invoke(vacancy)
        }
    }
}
