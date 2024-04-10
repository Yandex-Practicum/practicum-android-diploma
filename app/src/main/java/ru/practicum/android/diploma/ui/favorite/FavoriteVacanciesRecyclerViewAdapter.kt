package ru.practicum.android.diploma.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavoriteVacanciesRecyclerViewAdapter(
    private val vacancies: List<VacancyDetails>
) : RecyclerView.Adapter<FavoriteVacanciesRecyclerViewViewHolder>() {

    var vacancyClicked: (VacancyDetails) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVacanciesRecyclerViewViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.vacancy_view,
                parent,
                false
            )
        return FavoriteVacanciesRecyclerViewViewHolder(view, vacancyClicked)
    }

    override fun onBindViewHolder(holder: FavoriteVacanciesRecyclerViewViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    override fun getItemCount(): Int = vacancies.size
}
