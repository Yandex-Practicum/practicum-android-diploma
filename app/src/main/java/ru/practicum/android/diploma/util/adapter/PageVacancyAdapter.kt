package ru.practicum.android.diploma.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import ru.practicum.android.diploma.databinding.SearchItemViewBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class PageVacancyAdapter(
    val clickItem: (Vacancy) -> Unit
) : PagingDataAdapter<Vacancy, SearchItemViewHolder>(ArticleDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchItemViewHolder(SearchItemViewBinding.inflate(layoutInflater, parent, false), clickItem, {})
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
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
        return oldItem == newItem
    }
}
