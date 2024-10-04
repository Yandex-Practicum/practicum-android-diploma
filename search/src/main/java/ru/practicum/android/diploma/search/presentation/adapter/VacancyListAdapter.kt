package ru.practicum.android.diploma.search.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.ui.databinding.ItemVacancyBinding

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
        val layoutInspector = LayoutInflater.from(parent.context)
        return VacancyListViewHolder(ItemVacancyBinding.inflate(layoutInspector, parent, false))
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
