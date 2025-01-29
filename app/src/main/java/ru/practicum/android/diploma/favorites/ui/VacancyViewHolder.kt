package ru.practicum.android.diploma.favorites.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.util.Converter
import ru.practicum.android.diploma.search.domain.model.VacancyItems

class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName: TextView = itemView.findViewById(R.id.job)
    val tvEmployer: TextView = itemView.findViewById(R.id.employer)
    val ivIcon: ImageView = itemView.findViewById(R.id.vacancyIcon)
    val tvSalary: TextView = itemView.findViewById(R.id.salary)

    fun bind(
        item: VacancyItems,
        onItemClickListener: OnItemClickListener?
    ) {
        tvName.text = getJobDescription(item.name, item.areaName)
        tvEmployer.text = item.employer
        tvSalary.text = Converter.convertSalaryToString(
            item.salary?.from,
            item.salary?.to,
            item.salary?.currency
        )

        Glide.with(itemView)
            .load(item.iconUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_empty_ph)
            .into(ivIcon)

        itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    private fun getJobDescription(name: String, areaName: String): String {
        return when {
            areaName.isEmpty() -> name
            else -> "$name, $areaName"
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: VacancyItems)
    }
}
