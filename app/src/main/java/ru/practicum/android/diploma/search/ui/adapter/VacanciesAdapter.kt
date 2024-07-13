package ru.practicum.android.diploma.search.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.domain.models.Vacancy
import kotlin.math.min

class VacanciesAdapter(
    val onClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacancyViewHolder>() {
    private var vacancies = mutableListOf<Vacancy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(parent)
    }

    override fun getItemCount(): Int = vacancies.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = vacancies[position]
        holder.apply {
            bind(vacancy)
            itemView.setOnClickListener { onClick(vacancy) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newVacancies: List<Vacancy>) {
        if (newVacancies.isNotEmpty()
            && newVacancies.slice(0 until min(vacancies.size, newVacancies.size)) == vacancies
        ) {
            val oldSize = vacancies.size
            val countNew = newVacancies.size - oldSize
            vacancies += newVacancies.slice(oldSize until newVacancies.size)
            notifyItemRangeChanged(oldSize, countNew)
        } else {
            vacancies.clear()
            vacancies += newVacancies
            notifyDataSetChanged()
        }
    }

    fun clearItems() {
        setItems(emptyList())
    }
}
