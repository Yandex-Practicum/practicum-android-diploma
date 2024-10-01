package ru.practicum.android.diploma.search.presentation.viewmodel

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacancyListAdapter(private val clickListener: VacancyClickListener) :
    RecyclerView.Adapter<VacancyListViewHolder>() {
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VacancyListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancy: Vacancy)
    }
}
