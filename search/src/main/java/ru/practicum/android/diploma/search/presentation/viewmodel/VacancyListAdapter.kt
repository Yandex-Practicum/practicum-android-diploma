package ru.practicum.android.diploma.search.presentation.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.R

class VacancyListAdapter(private val clickListener: VacancyClickListener) :
    RecyclerView.Adapter<VacancyListViewHolder>() {

    private var vacancies = ArrayList<Vacancy>()
    fun setVacancies(vacancyList: ArrayList<Vacancy>) {
        vacancies = vacancyList
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vacancy_view, parent, false)
        return VacancyListViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyListViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener {
            clickListener.onVacancyClick(vacancies[position])
        }
    }

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancy: Vacancy)
    }
}
