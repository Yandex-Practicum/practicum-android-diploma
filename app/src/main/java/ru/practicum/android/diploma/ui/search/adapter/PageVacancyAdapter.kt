package ru.practicum.android.diploma.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy

class PageVacancyAdapter(context: Context) :
    PagingDataAdapter<Vacancy, VacancyViewHolder>(ArticleDiffItemCallback) {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(layoutInflater.inflate(R.layout.vacancy, parent, false))
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
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
