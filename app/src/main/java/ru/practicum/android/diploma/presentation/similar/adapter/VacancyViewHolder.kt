package ru.practicum.android.diploma.presentation.similar.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.mok.Vacancy
import ru.practicum.android.diploma.util.CurrencyLogoCreator

class VacancyViewHolder(private val binding:ItemVacancyBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        model: Vacancy,
        listener: VacanciesAdapter.ClickListener
    ) {

        itemView.setOnClickListener {
            listener.onClick(model)
        }

        if (model.employer?.logoUrls != null) {
            Glide.with(itemView)
                .load(model.employer.logoUrls.original)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.logoRv)
        } else {
            // Если logo_urls равен null, загружаем изображение по умолчанию
            Glide.with(itemView)
                .load(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.logoRv)
        }

        binding.headVacancyRv.text = "${model.name}, ${model.area?.name}"
        binding.employerRv.text = model.employer?.name
        if (model.salary != null) {
            val text = CurrencyLogoCreator.getSymbol(model.salary.currency)
            if (model.salary.from != null && model.salary.to != null) {
                binding.salaryRv.text = "От ${model.salary.from} до ${model.salary.to} $text"
            } else if (model.salary.from == null && model.salary.to != null) {
                binding.salaryRv.text = "До ${model.salary.to} $text"
            } else {
                binding.salaryRv.text = "От ${model.salary.from} $text"
            }

        } else {
            binding.salaryRv.text = "Зарплата не указана"
        }
    }
}