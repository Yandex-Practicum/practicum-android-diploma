package ru.practicum.android.diploma.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class FavoriteFragmentRecyclerViewAdapter(
    private val vacancies: List<Vacancy>
) : RecyclerView.Adapter<FavoriteFragmentRecyclerViewViewHolder>() {

    var vacancyClicked: (Vacancy) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFragmentRecyclerViewViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.vacancy_view,
                parent,
                false
            )
        return FavoriteFragmentRecyclerViewViewHolder(view, vacancyClicked)
    }

    override fun onBindViewHolder(holder: FavoriteFragmentRecyclerViewViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    override fun getItemCount(): Int = vacancies.size
}
