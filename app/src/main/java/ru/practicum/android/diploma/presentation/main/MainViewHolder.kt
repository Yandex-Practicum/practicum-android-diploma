package ru.practicum.android.diploma.presentation.main

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class MainViewHolder(private val binding: VacancyItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy?, onClick: (String) -> Unit) {

        if (vacancy == null) return

        binding.department.text = vacancy.employer
        binding.salary.text = vacancy.salary
        binding.tvVacancyName.text = "${vacancy.name}, ${vacancy.area}"

        Glide.with(binding.ivCompany)
            .load(vacancy.employerImgUrl)
            .placeholder(R.drawable.placeholder_company_icon)
            .centerCrop()
            .into(binding.ivCompany)

        binding.root.setOnClickListener { onClick.invoke(vacancy.id) }
    }
}
