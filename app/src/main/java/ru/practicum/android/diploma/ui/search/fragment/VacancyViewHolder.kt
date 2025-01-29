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
        binding.textViewVacancyEmployer.text = model.employer?.name
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
        val currencyInSymbol = when (currency) {
            "AZN" -> itemView.context.getString(R.string.AZN)
            "BYR" -> itemView.context.getString(R.string.BYR)
            "EUR" -> itemView.context.getString(R.string.EUR)
            "GEL" -> itemView.context.getString(R.string.GEL)
            "KGS" -> itemView.context.getString(R.string.KGS)
            "KZT" -> itemView.context.getString(R.string.KZT)
            "RUR" -> itemView.context.getString(R.string.RUR)
            "UAH" -> itemView.context.getString(R.string.UAH)
            "USD" -> itemView.context.getString(R.string.USD)
            "UZS" -> itemView.context.getString(R.string.UZS)
            else -> currency
        }
        return when {
            from != null && to == null -> "от ${formattedSalary(from)} $currencyInSymbol"
            from == null && to != null -> "до ${formattedSalary(to)} $currencyInSymbol"
            from != null && to != null -> "от ${formattedSalary(from)} до ${formattedSalary(to)} $currencyInSymbol"
            else -> itemView.context.getString(R.string.salary_not_specified)
        }
    }

    private fun formattedSalary(value: Int): String {
        return "%,d".format(value).replace(",", " ")
    }
}
