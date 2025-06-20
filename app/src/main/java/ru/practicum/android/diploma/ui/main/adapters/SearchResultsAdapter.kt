package ru.practicum.android.diploma.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.ui.common.dpToPx

class VacancyDiffCallback : DiffUtil.ItemCallback<Vacancy>() {
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        // Например, по ID
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }
}

class SearchResultsAdapter(
    private val clickListener: VacancyClickListener
) : ListAdapter<Vacancy, SearchResultsAdapter.SearchResultsItemViewHolder>(VacancyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsItemViewHolder {
        return SearchResultsItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SearchResultsItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchResultsItemViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val employeTitle: TextView = itemView.findViewById(R.id.employee_title)
        private val salary: TextView = itemView.findViewById(R.id.salary)
        private val artwork: ImageView = itemView.findViewById(R.id.artwork)

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(R.layout.vacancy_view, parent, false)
        )

        fun bind(vacancy: Vacancy) {
            title.text = vacancy.title
            employeTitle.text = vacancy.employerTitle
            salary.text = formatSalary(itemView.context, vacancy.salaryRange)

            itemView.setOnClickListener {
                clickListener.onVacancyClick(vacancy)
            }

            Glide.with(itemView)
                .load(vacancy.logoUrl)
                .placeholder(R.drawable.vacancy_artwork_placeholder)
                .error(R.drawable.vacancy_artwork_placeholder)
                .fitCenter()
                .transform(RoundedCorners(dpToPx(2F, itemView.context)))
                .into(artwork)
        }
    }

    private fun formatSalary(context: Context, salaryRange: Vacancy.VacancySalaryRange?): String {
        if (salaryRange == null || salaryRange.from == null && salaryRange.to == null) {
            return context.getString(R.string.salary_not_specified)
        }

        val currencySymbol = when (salaryRange.currency.uppercase()) {
            "RUB", "RUR" -> context.getString(R.string.currency_rub)
            "BYR" -> context.getString(R.string.currency_byr)
            "USD" -> context.getString(R.string.currency_usd)
            "EUR" -> context.getString(R.string.currency_eur)
            "KZT" -> context.getString(R.string.currency_kzt)
            "UAH" -> context.getString(R.string.currency_uah)
            "AZN" -> context.getString(R.string.currency_azn)
            "UZS" -> context.getString(R.string.currency_uzs)
            "GEL" -> context.getString(R.string.currency_gel)
            "KGT" -> context.getString(R.string.currency_kgt)
            else -> salaryRange.currency
        }

        val formattedFrom = salaryRange.from?.let { "%,d".format(it).replace(',', ' ') }
        val formattedTo = salaryRange.to?.let { "%,d".format(it).replace(',', ' ') }

        return when {
            salaryRange.from != null && salaryRange.to != null ->
                context.getString(R.string.salary_range, formattedFrom, formattedTo, currencySymbol)

            salaryRange.from != null ->
                context.getString(R.string.salary_from, formattedFrom, currencySymbol)

            else ->
                context.getString(R.string.salary_to, formattedTo, currencySymbol)
        }
    }

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancy: Vacancy)
    }
}
