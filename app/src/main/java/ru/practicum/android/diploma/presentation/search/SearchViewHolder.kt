package ru.practicum.android.diploma.presentation.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.mok.Vacancy

class SearchViewHolder(private val binding: ItemVacancyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Vacancy) {
        binding.headVacancyRv.text = item.name
        binding.employerRv.text = item.employer?.name ?: ""
        binding.salaryRv.text = item.salary?.currency ?: ""
        Glide.with(itemView)
            .load(item.employer?.url)
            .placeholder(R.drawable.logo)
            .centerCrop()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.logo_corner_radius)))
            .into(binding.logoRv)

    }
}
