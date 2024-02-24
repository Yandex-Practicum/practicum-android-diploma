package ru.practicum.android.diploma.search.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.databinding.VacancyViewBinding

class VacancyViewHolder(private val binding: VacancyViewBinding) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(shortVacancy: ShortVacancy) {
        Glide.with(binding.root)
            .load(shortVacancy.employerLogoUrl)
            .placeholder(R.drawable.placeholder_vacancy)
            .centerInside()
            .into(binding.vacancyCover)
        binding.vacancyName.text = "${shortVacancy.name}, ${shortVacancy.city}"
        binding.companyName.text = "$shortVacancy.companyName"
        binding.salary.text = "${shortVacancy.salaryFrom} ${shortVacancy.salaryTo}"
    }
}
