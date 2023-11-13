package ru.practicum.android.diploma.presentation.filter.chooseArea.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Area

class AreaAdapter<T : Area>(
    val items: List<T>,
    private val clickListener: (T) -> Unit
) : RecyclerView.Adapter<AreaViewHolder<T>>() {

    private var positionChecked = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder<T> =
        AreaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.industry_area_item, parent, false),
            clickListener
        )

    override fun onBindViewHolder(holder: AreaViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    // Функциональный интерфейс для обработки кликов
    interface ClickListener<T> {
        fun onItemClicked(item: T)
    }
}
