package ru.practicum.android.diploma.favorites.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.model.VacancyItems

class VacancyAdapter : RecyclerView.Adapter<VacancyViewHolder>() {
    var items: MutableList<VacancyItems> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value.toMutableList()
            notifyDataSetChanged()
        }

    var onItemClickListener: VacancyViewHolder.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)

        return VacancyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(
            item = items[position],
            onItemClickListener = onItemClickListener
        )
    }
}
