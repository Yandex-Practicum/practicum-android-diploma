package ru.practicum.android.diploma.ui.vacancy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.VacanciesPreview

class VacancyFavoriteAdapter(
    private val onVacancyClicked: (String) -> Unit,
) : ListAdapter<VacanciesPreview, VacancyFavoriteViewHolder>(VacancyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyFavoriteViewHolder {
        val binding = VacancyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VacancyFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyFavoriteViewHolder, position: Int) {
        val vacancy = getItem(position)
        holder.bind(vacancy)
        holder.itemView.setOnClickListener {
            onVacancyClicked(vacancy.vacancyId)
        }
    }

    class VacancyDiffCallback : DiffUtil.ItemCallback<VacanciesPreview>() {

        override fun areItemsTheSame(oldItem: VacanciesPreview, newItem: VacanciesPreview): Boolean {
            return oldItem.vacancyId == newItem.vacancyId
        }

        override fun areContentsTheSame(oldItem: VacanciesPreview, newItem: VacanciesPreview): Boolean {
            return oldItem == newItem
        }
    }
}
