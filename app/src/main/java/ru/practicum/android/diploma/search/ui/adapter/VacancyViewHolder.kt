package ru.practicum.android.diploma.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacancyViewHolder(
    parentView: ViewGroup,
    val binding: VacancyItemBinding = VacancyItemBinding.inflate(
        LayoutInflater.from(parentView.context), parentView, false
    ),
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy) {
        binding.tvTitle.text = itemView.resources.getString(
            R.string.vacancy_item_title_template, vacancy.name, vacancy.area
        )
        binding.tvSalary.text = vacancy.salary
        binding.tvCompany.text = vacancy.company
        Glide.with(itemView)
            .load(vacancy.icon)
            .placeholder(R.drawable.ic_placeholder_logo)
            .centerInside()
            .into(binding.ivIcon)
        binding.ivIcon.clipToOutline = true
    }
}
