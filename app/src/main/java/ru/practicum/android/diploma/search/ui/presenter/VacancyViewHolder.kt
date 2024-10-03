package ru.practicum.android.diploma.search.ui.presenter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyCardBinding
import ru.practicum.android.diploma.search.domain.models.VacancySearch

class VacancyViewHolder(private val binding: VacancyCardBinding) : RecyclerView.ViewHolder(binding.root) {

    private val corner by lazy { itemView.resources.getDimension(R.dimen.radius_size_12).toInt() }

    fun bind(vacancy: VacancySearch) {
        binding.vacancyName.text = vacancy.name
        binding.companyName.text = vacancy.company
        binding.salary.text = vacancy.salary

        Glide
            .with(itemView)
            .load(vacancy.logo)
            .fitCenter()
            .placeholder(R.drawable.ic_placeholder_32px)
            .transform(RoundedCorners(corner))
            .into(binding.image)

    }

}
