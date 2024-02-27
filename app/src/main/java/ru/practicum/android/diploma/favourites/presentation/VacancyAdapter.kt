package ru.practicum.android.diploma.favourites.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.DetailVacancy

class VacancyAdapter(
    private val clickVacancyListener: ClickVacancyListener,
    private val vacancys: List<DetailVacancy>
) : RecyclerView.Adapter<FavouritesVacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesVacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vacancy_view, parent, false)
        return FavouritesVacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesVacancyViewHolder, position: Int) {
        holder.bind(vacancys[position])
        holder.itemView.setOnClickListener {
            clickVacancyListener.onVacancyClick(vacancys[position].id)
        }
    }

    override fun getItemCount() = vacancys.size

    fun interface ClickVacancyListener {
        fun onVacancyClick(vacancyId: Long)
    }
}
