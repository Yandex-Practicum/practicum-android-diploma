package ru.practicum.android.diploma.ui.vacancy.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

class VacancyAdapter(private val clickListener: VacancyClickListener) : RecyclerView.Adapter<VacancyViewHolder>() {
    val vacancies: MutableList<Vacancy> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(parent)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
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
