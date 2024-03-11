package ru.practicum.android.diploma.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.detail.VacancyDetail

class FavoriteViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.vacancy_item, parent, false)
) {

    private val nameText: TextView = itemView.findViewById(R.id.tv_vacancy_name)
    private val departmentText: TextView = itemView.findViewById(R.id.department)
    private val salaryText: TextView = itemView.findViewById(R.id.salary)
    private val image: ImageView = itemView.findViewById(R.id.iv_company)

    fun bind(vacancy: VacancyDetail) {
        nameText.text = "${vacancy.name}, ${vacancy.area}"
        departmentText.text = vacancy.employerName
        salaryText.text = vacancy.salary?.replace(",", " ") ?: vacancy.salary

        Glide.with(itemView)
            .load(vacancy.employerUrl)
            .placeholder(R.drawable.placeholder_company_icon)
            .centerCrop()
            .into(image)
    }
}

