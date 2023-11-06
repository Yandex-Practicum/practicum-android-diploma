package ru.practicum.android.diploma.presentation.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.SalaryPresenter

class SearchViewHolder(private val binding: ItemVacancyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Vacancy) {
        binding.headVacancyRv.text = item.name
        binding.employerRv.text = item.employerName
        binding.salaryRv.text = SalaryPresenter().showSalary(item.salary)
        Glide.with(itemView)
            .load(item.employerLogoUrl)
            .placeholder(R.drawable.logo)
            .centerCrop()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.logo_corner_radius)))
            .into(binding.logoRv)

    }
}