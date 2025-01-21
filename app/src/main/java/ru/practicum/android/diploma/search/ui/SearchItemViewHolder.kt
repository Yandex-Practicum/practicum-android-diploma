package ru.practicum.android.diploma.search.ui

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.search.domain.model.VacancyItems

class SearchItemViewHolder(
    private val binding: ItemVacancyBinding,
    onItemClicked: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(vacancy: VacancyItems) {
        with(vacancy) {
            uploadImage(iconUrl, binding.vacancyIcon)
            binding.job.text = getJobDescription(name, areaName)
            binding.employer.text = employer
            binding.salary.text = salary?.getSalaryToString()
        }
    }

    private fun uploadImage(url: String?, imageView: ImageView) {
        Glide.with(binding.root.context)
            .load(url)
            .placeholder(R.drawable.ic_empty_ph)
            .fitCenter()
            .into(imageView)
    }

    private fun getJobDescription(job: String, areaName: String): String {
        return when {
            areaName.isEmpty() -> job
            else -> "${job}, ${areaName}"
        }
    }
}
