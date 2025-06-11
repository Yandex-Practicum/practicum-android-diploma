package ru.practicum.android.diploma.ui.main.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

class SearchAdapter(private val clickListener: VacancyClickListener) : RecyclerView.Adapter<SearchViewHolder>() {
    val vacancies: MutableList<Vacancy> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener { clickListener.onVacancyClick(vacancies[position]) }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    interface VacancyClickListener {
        fun onVacancyClick(vacancy: Vacancy)
    }
}
