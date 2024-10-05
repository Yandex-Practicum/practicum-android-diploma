package ru.practicum.android.diploma.search.presentation.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.commonutils.Utils.formatSalary
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.ui.R
import ru.practicum.android.diploma.ui.databinding.ItemVacancyBinding

private const val RADIUS_ROUND_VIEW = 12f

class VacancyListViewHolder(
    private val binding: ItemVacancyBinding
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(model: Vacancy) {
        binding.itemNameVacancyAndLocation.text = model.title + ", " + model.area.name + ""
        binding.itemNameCompany.text = model.companyName
        binding.itemSalarySize.text =
            itemView.context.formatSalary(model.salaryMin, model.salaryMax, model.salaryCurrency)

        Glide.with(itemView)
            .load(model.companyLogo)
            .placeholder(R.drawable.placeholder_logo_item_favorite)
            .centerCrop()
            .transform(
                RoundedCorners(
                    Utils.doToPx(RADIUS_ROUND_VIEW, itemView.context.applicationContext)
                )
            ).transform().into(binding.itemLogoVacancy)
    }
}
