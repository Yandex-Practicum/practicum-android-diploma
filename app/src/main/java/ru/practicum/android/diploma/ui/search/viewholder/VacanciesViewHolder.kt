package ru.practicum.android.diploma.ui.search.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.commons.data.Constant
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.VacancyModel

class VacanciesViewHolder(
    private val binding: VacancyItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        model: VacancyModel
    ) {
        binding.vacancyName.text = model.vacancyName
        binding.companyName.text = model.companyName
        binding.companySalary.text = model.salary

        Glide.with(itemView)
            .load(model.logoUrls[0])
            .centerCrop()
            .transform(RoundedCorners(Constant.COMPANY_LOGO_RADIUS_12_PX))
            .placeholder(R.drawable.ic_logo)
            .into(binding.companyLogo)
    }

}
