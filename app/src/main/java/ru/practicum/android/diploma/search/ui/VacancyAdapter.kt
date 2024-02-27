package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.databinding.VacancyViewBinding

class VacancyAdapter(
    private val onItemClick: ((ShortVacancy) -> Unit)? = null
) : ListAdapter<ShortVacancy, RecyclerView.ViewHolder>(VacancyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VacancyViewBinding.inflate(layoutInflater, parent, false)
        return VacancyViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VacancyViewHolder) {
            holder.bind(currentList[position], onItemClick)
        }
    }
}
