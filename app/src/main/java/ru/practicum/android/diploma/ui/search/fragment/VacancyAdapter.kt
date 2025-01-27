package ru.practicum.android.diploma.ui.search.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.diffutils.VacancyDiffCallback

class VacancyAdapter(
    private val onProductClick: (vacancyId: Long) -> Unit,
) : ListAdapter<Vacancy, VacancyViewHolder>(VacancyDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): VacancyViewHolder {
        val binding = ItemVacancyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VacancyViewHolder(binding, onProductClick)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = getItem(position)
        holder.bind(vacancy)
    }
}
