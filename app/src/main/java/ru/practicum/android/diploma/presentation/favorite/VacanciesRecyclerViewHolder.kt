package ru.practicum.android.diploma.presentation.favorite

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.search.models.VacancyShort

class VacanciesRecyclerViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {
    private var vacancyNameAndCityTV: TextView = parentView.findViewById(R.id.vacancyNameAndCityTVRecycler)
    private var companyNameTV: TextView = parentView.findViewById(R.id.companyNameTVRecycler)
    private var salaryTV: TextView = parentView.findViewById(R.id.salaryTVRecycler)
    private var employerLogoIV: ImageView = parentView.findViewById(R.id.employerLogoIVRecycler)
    fun bind(vacancy: VacancyShort) {
        vacancyNameAndCityTV.text = itemView.context.getString(R.string.vacancy_item_title, vacancy.name, vacancy.area)
        companyNameTV.text = vacancy.employer
        salaryTV.text = "${vacancy.salary} ${vacancy.currency}"
        Glide.with(itemView)
            .load(Uri.parse(vacancy.artLink))
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .fitCenter()
            .into(employerLogoIV)
    }
}
