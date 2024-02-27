package ru.practicum.android.diploma.presentation.main

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.databinding.VacancyItemBinding

class MainViewHolder(private val binding: VacancyItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy) {
        binding.department.text = vacancy.employer
        binding.salary.text = vacancy.salary
        binding.tvVacancyName.text = vacancy.name

        Glide.with(itemView.context)
            .load(vacancy.employerImgUrl)
            .placeholder(R.drawable.ic_vacancy_placeholder)
            .centerCrop()
            .into(binding.ivCompany)
    }
}
