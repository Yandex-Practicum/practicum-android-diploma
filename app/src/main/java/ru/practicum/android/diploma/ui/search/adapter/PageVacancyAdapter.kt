package ru.practicum.android.diploma.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy

class PageVacancyAdapter(
    val clickItem: (Vacancy) -> Unit
) : PagingDataAdapter<Vacancy, VacancyViewHolder>(ArticleDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vacancy, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        getItem(position)?.let { vacancy ->
            holder.bind(vacancy)
            holder.itemView.setOnClickListener { clickItem(vacancy) }
        }

    }
}

private object ArticleDiffItemCallback : DiffUtil.ItemCallback<Vacancy>() {
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return true
    }
}
