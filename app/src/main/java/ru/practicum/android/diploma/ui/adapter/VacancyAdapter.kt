package ru.practicum.android.diploma.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyCard

class VacancyAdapter : RecyclerView.Adapter<VacancyViewHolder>() {

    private var items: List<VacancyCard> = emptyList()

    fun submitList(newItems: List<VacancyCard>?) {
        if (newItems == null) return
        if (items == newItems) return

        val diffResult = DiffUtil.calculateDiff(
            VacancyDiffCallback(items, newItems)
        )
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vacancy, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
