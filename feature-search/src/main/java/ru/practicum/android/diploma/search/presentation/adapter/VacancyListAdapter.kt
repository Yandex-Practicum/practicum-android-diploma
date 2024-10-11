package ru.practicum.android.diploma.search.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.presentation.viewmodel.VacancyListViewModel
import ru.practicum.android.diploma.ui.databinding.ItemVacancyBinding

internal class VacancyListAdapter(
    private val clickListener: VacancyClickListener,
    private val viewModel: VacancyListViewModel
) :
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
        return VacancyListViewHolder(ItemVacancyBinding.inflate(layoutInspector, parent, false), viewModel)
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
