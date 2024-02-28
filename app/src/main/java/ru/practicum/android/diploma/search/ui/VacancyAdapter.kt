package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.databinding.VacancyViewBinding

class VacancyAdapter(
    private val onItemClick: ((ShortVacancy) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var vacancyList = mutableListOf<ShortVacancy>()

    fun updateList(vacancies: List<ShortVacancy>) {
        val diff = VacancyDiffCallback(vacancyList, vacancies)
        val result = DiffUtil.calculateDiff(diff)
        vacancyList.clear()
        vacancyList.addAll(vacancies)
        result.dispatchUpdatesTo(this)
    }

    fun pagination(newVacancies: List<ShortVacancy>) {
        val newList = mutableListOf<ShortVacancy>().apply {
            addAll(vacancyList)
            addAll(newVacancies)
        }
        val diff = VacancyDiffCallback(vacancyList, newList)
        val result = DiffUtil.calculateDiff(diff)
        vacancyList.addAll(newVacancies)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VacancyViewBinding.inflate(layoutInflater, parent, false)
        return VacancyViewHolder(binding)
    }

    override fun getItemCount(): Int = vacancyList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VacancyViewHolder) {
            holder.bind(vacancyList[position], onItemClick)
        }
    }
}
