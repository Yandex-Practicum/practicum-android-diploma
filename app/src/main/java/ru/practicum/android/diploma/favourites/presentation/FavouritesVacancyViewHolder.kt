package ru.practicum.android.diploma.favourites.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.util.getSalaryStr
import ru.practicum.android.diploma.util.getVacancyTitle

class FavouritesVacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val vacancyName: TextView
    private val companyName: TextView
    private val salary: TextView
    private val vacancyCover: ImageView

    private val ROUND_RADIUS = 10

    init {
        vacancyName = itemView.findViewById(R.id.vacancyName)
        companyName = itemView.findViewById(R.id.companyName)
        salary = itemView.findViewById(R.id.salary)
        vacancyCover = itemView.findViewById(R.id.vacancyCover)
    }

    fun bind(model: DetailVacancy) {
        vacancyName.text = getVacancyTitle(model.name, model.city)
        companyName.text = model.employerName
        salary.text = getSalaryStr(model.salaryFrom, model.salaryTo, model.currency, itemView.context)
        Glide.with(itemView)
            .load(model.employerLogoUrl)
            .placeholder(R.drawable.placeholder_vacancy)
            .centerInside()
            .transform(RoundedCorners(ROUND_RADIUS))
            .into(vacancyCover)
    }

}
