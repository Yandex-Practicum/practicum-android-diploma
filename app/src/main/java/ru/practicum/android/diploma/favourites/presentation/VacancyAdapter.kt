package ru.practicum.android.diploma.favourites.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.DetailVacancy

class VacancyAdapter(
    private val clickVacancyListener: ClickVacancyListener,
) : RecyclerView.Adapter<FavouritesVacancyViewHolder>() {

    private var vacancies = listOf<DetailVacancy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesVacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vacancy_view, parent, false)
        return FavouritesVacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesVacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener {
            clickVacancyListener.onVacancyClick(vacancies[position].id)
        }
    }

    fun updateVacancyList(vacancyList: List<DetailVacancy>) {
        vacancies = vacancyList
    }

    override fun getItemCount() = vacancies.size

    fun interface ClickVacancyListener {
        fun onVacancyClick(vacancyId: Long)
    }
}
