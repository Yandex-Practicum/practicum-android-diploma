package ru.practicum.android.diploma.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchAdapter(
    private val clickListener: VacancyClickListener
) : RecyclerView.Adapter<SearchViewHolder>() {
    var items = ArrayList<Vacancy>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder.from(parent)

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val vacancy = items[position]
        holder.bind(vacancy)
        holder.itemView.setOnClickListener {
            clickListener.onVacancyClick(vacancy)
        }
    }

    override fun getItemCount(): Int = items.size

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancy: Vacancy)
    }

    fun setVacancies(vacancies: List<Vacancy>) {
        items = ArrayList(vacancies)
        notifyDataSetChanged()
    }
}
