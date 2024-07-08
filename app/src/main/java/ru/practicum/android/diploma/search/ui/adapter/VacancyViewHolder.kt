package ru.practicum.android.diploma.search.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacancyViewHolder(private val binding: VacancyItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy) {
        binding.tvTitle.text = vacancy.name
        binding.tvArea.text = vacancy.area
        binding.tvSalary.text = vacancy.salary
        binding.tvCompany.text = vacancy.company
        Glide.with(itemView)
            .load(vacancy.icon)
            .placeholder(R.drawable.ic_placeholder_vacancy)
            .centerCrop()
            .into(binding.ivIcon)
    }
}
