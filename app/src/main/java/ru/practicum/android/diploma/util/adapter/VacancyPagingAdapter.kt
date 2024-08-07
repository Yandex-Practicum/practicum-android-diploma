package ru.practicum.android.diploma.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.databinding.SearchItemViewBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyPagingAdapter(
    val clickItem: (Vacancy) -> Unit
) : PagingDataAdapter<Vacancy, VacancyViewHolder>(VacancyDiffItemCallback) {

    // Создает ViewHolder для элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VacancyViewHolder(SearchItemViewBinding.inflate(layoutInflater, parent, false), clickItem, {})
    }

    // Связывает данные вакансии с ViewHolder
    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        getItem(position)?.let { vacancy ->
            holder.bind(vacancy)
            holder.itemView.setOnClickListener { clickItem(vacancy) }
        }
    }
}

private object VacancyDiffItemCallback : DiffUtil.ItemCallback<Vacancy>() {

    // Проверяет, являются ли два элемента списка одинаковыми по идентификатору
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }

    // Проверяет, одинаково ли содержимое двух элементов списка
    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }
}
