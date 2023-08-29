package ru.practicum.android.diploma.util.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.data.dto.VacancyDto
import ru.practicum.android.diploma.util.viewholder.VacancyViewHolder

class VacancyAdapter(private val vacancies: ArrayList<VacancyDto>) :
    RecyclerView.Adapter<VacancyViewHolder>() {

    var itemClickListener: ((Int, VacancyDto) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = vacancies[position]
        holder.bind(vacancy)
        holder.itemView.setOnClickListener() {
            itemClickListener?.invoke(position, vacancy)
        }
    }

    fun setVacancies(newVacancy: List<VacancyDto>?) {
        vacancies.clear()
        if (!newVacancy.isNullOrEmpty()) {
            vacancies.addAll(newVacancy)
        }
        notifyDataSetChanged()
    }
}