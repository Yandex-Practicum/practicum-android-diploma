package ru.practicum.android.diploma.search.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacanciesAdapter(
    val onClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacancyViewHolder>() {
    private val vacancies = mutableListOf<Vacancy>()

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

    fun addItems(newVacancies: List<Vacancy>) {
        if (newVacancies.isEmpty())
            return
        val originalSize = vacancies.size
        vacancies += newVacancies
        notifyItemRangeInserted(originalSize, newVacancies.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        vacancies.clear()
        notifyDataSetChanged()
    }
}
