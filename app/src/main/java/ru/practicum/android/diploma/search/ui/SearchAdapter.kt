package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemDescriptionBinding
import ru.practicum.android.diploma.search.domain.Vacancy


class SearchAdapter () : RecyclerView.Adapter<SearchViewHolder>() {
    var list = listOf<Vacancy>()
    var onClick: ((Vacancy) -> Unit)? = null
    var onLongClick: ((Vacancy) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemDescriptionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun getItemCount(): Int = list.size
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val item = list[pos]
        holder.itemView.setOnClickListener {
            onClick?.invoke(item)
        }
        holder.itemView.setOnLongClickListener { onLongClick?.invoke(item); true }
    }
}