package ru.practicum.android.diploma.ui.favorite

import android.icu.text.DecimalFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.vacacy.Salary

class FavoriteVacanciesRecyclerViewViewHolder(
    itemView: View,
    private val vacancyClicked: (VacancyDetails) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val vacancyCompanyLogo: ImageView = itemView.findViewById(R.id.iv_logo)
    private val vacancyTitle: TextView = itemView.findViewById(R.id.tv_vacancy_name)
    private val vacancyCompany: TextView = itemView.findViewById(R.id.tv_vacancy_type)
    private val vacancySalary: TextView = itemView.findViewById(R.id.tv_vacancy_salary)

    fun bind(model: VacancyDetails) {
        vacancyTitle.text = model.name
        vacancyCompany.text = model.employer?.name ?: ""
        vacancySalary.text = formatSalary(model.salary).replace(",", " ")

        Glide
            .with(itemView)
            .load(model.employer?.logoUrls?.original ?: "")
            .placeholder(R.drawable.spiral)
            .transform(FitCenter())
            .into(vacancyCompanyLogo)

        itemView.setOnClickListener { vacancyClicked(model) }
    }


    private fun formatSalary(salary: Salary?): String {
        if (salary == null) return "Зарплата не указана"

        val currency = when (salary.currency) {
            "RUR" -> "₽"
            "EUR" -> "€"
            "KZT" -> "₸"
            "AZN" -> "\u20BC"
            "USD" -> "$"
            "BYR" -> "\u0042\u0072"
            "GEL" -> "\u20BE"
            "UAH" -> "\u20b4"
            "UZS" -> "Soʻm"
            else -> ""
        }

        val stringBuilder = StringBuilder()

        salary.from?.let {
            stringBuilder.append("от ${formatSalary(salary.from)} ")
        }

        salary.to?.let {
            stringBuilder.append("до ${formatSalary(salary.to)} ")
        }
        stringBuilder.append("$currency")

        return stringBuilder.toString()
    }

    private fun formatSalary(salary: Int): String {
        val df = DecimalFormat()
        df.isGroupingUsed = true
        df.groupingSize = 3

        return df.format(salary)
    }
}
