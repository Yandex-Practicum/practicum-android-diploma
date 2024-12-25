package ru.practicum.android.diploma.ui.favorites.activity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyListItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.DimenConvertor

class VacancyViewHolder(
    private val binding: VacancyListItemBinding,
    onItemClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(model: Vacancy) {
        Glide.with(binding.root)
            .load(model.employerLogoUrl)
            .placeholder(R.drawable.company_logo_placeholder)
            .centerCrop()
            .transform(
                RoundedCorners(
                    DimenConvertor.dpToPx(
                        NUMBER_OF_DP_FOR_ROUNDING_CORNERS,
                        binding.root.context
                    )
                )
            )
            .into(binding.ivCompanyLogo)

        binding.tvTitleOfVacancy.text = model.titleOfVacancy
        binding.tvCompanyName.text = model.employerName
        binding.tvVacancySalary.text = model.salary ?: binding.root.resources.getString(R.string.salary)
    }

    companion object {
        private const val NUMBER_OF_DP_FOR_ROUNDING_CORNERS = 12f
    }
}
