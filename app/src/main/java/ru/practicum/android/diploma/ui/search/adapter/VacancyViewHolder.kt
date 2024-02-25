package ru.practicum.android.diploma.ui.search.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.ConvertSalary

class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     val convertSalary = ConvertSalary()
    private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
    private val tvCompanyName: TextView = itemView.findViewById(R.id.tv_name_company)
    private val tvSalary: TextView = itemView.findViewById(R.id.tv_salary)
    private val ivUrl100: ImageView = itemView.findViewById(R.id.iv_logo)
    val radius = itemView.resources.getDimensionPixelSize(R.dimen.corner_radius_card_company_12)

    @SuppressLint("SetTextI18n")
    fun bind(item: Vacancy) {
        tvDescription.text = item.name
        tvCompanyName.text = item.employer
        tvSalary.text = convertSalary.formatSalaryWithCurrency(item.salaryFrom, item.salaryTo, item.currency)
        Glide.with(ivUrl100)
            .load(item.employerLogoUrls)
             .placeholder(R.drawable.placeholder_vacancy)
            .transform(RoundedCorners(radius))
            .into(ivUrl100)
    }
}
