package ru.practicum.android.diploma.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.main.Vacancy

class FavoriteViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.vacancy_item, parent, false)
) {

    private val _binding: VacancyItemBinding? = null
    private val binding get() = _binding!!

    fun bind(vacancy: Vacancy) {
        binding.tvVacancyName.text = vacancy.name
        binding.department.text = vacancy.employer
        binding.tvVacancyName.text = vacancy.name

        Glide.with(binding.ivCompany)
            .load(vacancy.employerImgUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(binding.ivCompany)
    }

}
