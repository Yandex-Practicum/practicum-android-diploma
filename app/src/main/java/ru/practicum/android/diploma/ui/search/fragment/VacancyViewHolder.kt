package ru.practicum.android.diploma.ui.search.fragment

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyViewHolder(
    private val binding: ItemVacancyBinding,
    private val onClick: (vacancy: Vacancy) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Vacancy) {
        binding.textViewVacancyTitle.text = model.name
        binding.textViewVacancyEmployer.text = model.name
        binding.textViewVacancySalary.text = model.salary?.from.toString()
        val image: ImageView = binding.imageViewVacancyLogo

        val radius = itemView.resources.getDimensionPixelSize(R.dimen.dp_12)

        Glide.with(itemView)
            .load(model.employer?.logoUrls?.size240)
            .placeholder(R.drawable.ic_droid_48)
            .error(R.drawable.ic_droid_48)
            .centerCrop()
            .transform(RoundedCorners(radius))
            .into(image)

        binding.root.setOnClickListener { _ -> onClick(model) }
    }
}
