package ru.practicum.android.diploma.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacanciesAdapter(
    val onClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacancyViewHolder>() {

    var vacancies = ArrayList<Vacancy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return VacancyViewHolder(VacancyItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int = vacancies.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener { onClick(vacancies[position]) }
    }
}
