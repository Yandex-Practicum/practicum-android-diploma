package ru.practicum.android.diploma.ui.search.fragment

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.format.dpToPx

private const val CORNER_RADIUS = 12

class VacancyViewHolder(
    private val binding: ItemVacancyBinding,
    private val onClick: (vacancyId: Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Vacancy) {
        binding.textViewVacancyTitle.text = getTitleWithCity(model.name, model.address?.city)
        binding.textViewVacancyEmployer.text = model.name
        binding.textViewVacancySalary.text =
            createSalaryInterval(model.salary?.from, model.salary?.to, model.salary?.currency)
        val image: ImageView = binding.imageViewVacancyLogo

        Glide.with(itemView)
            .load(model.employer?.logoUrls?.size240)
            .placeholder(R.drawable.ic_droid_48)
            .error(R.drawable.ic_droid_48)
            .transform(CenterCrop(), RoundedCorners(itemView.context.dpToPx(CORNER_RADIUS)))
            .into(image)

        binding.root.setOnClickListener { _ -> onClick(model.vacancyId) }
    }

    private fun getTitleWithCity(name: String?, city: String?): String? {
        return if (city == null) {
            name
        } else {
            "$name, $city"
        }

    }

    private fun createSalaryInterval(from: Int?, to: Int?, currency: String?): String {
        return when {
            from != null && to == null -> "от $from $currency"
            from == null && to != null -> "до $to $currency"
            from != null && to != null -> "от $from до $to $currency"
            else -> itemView.context.getString(R.string.salary_not_specified)
        }
    }
}
