package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchViewHolder(private val binding: ItemVacancyBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Vacancy) {
        binding.nameAreaName.text = item.name
        binding.industryName.text = item.employerName
        binding.salary.text = item.salaryTitle
        Glide.with(itemView)
            .load(item.logoUrl)
            .placeholder(R.drawable.ic_company_placeholder_48)
            .centerCrop()
            .transform(RoundedCorners(dpToPixel(CORNER_RADIUS)))
            .into(binding.companyImage)
    }

    private fun dpToPixel(dp: Float): Int {
        val density = itemView.resources.displayMetrics.density
        return (dp * density).toInt()
    }

    companion object {
        const val CORNER_RADIUS = 12f
        fun from(parent: ViewGroup): SearchViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemVacancyBinding.inflate(inflater, parent, false)
            return SearchViewHolder(binding)
        }
    }
}
