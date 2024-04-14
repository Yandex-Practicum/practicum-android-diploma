package ru.practicum.android.diploma.ui.search.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class VacancyAdapter(
    private val onClick: (Vacancy) -> Unit
) : ListAdapter<Vacancy, VacancyViewHolder>(VacancyDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VacancyViewBinding.inflate(inflater, parent, false)
        return VacancyViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}
