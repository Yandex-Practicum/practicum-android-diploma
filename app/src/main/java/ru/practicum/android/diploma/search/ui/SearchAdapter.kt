package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.databinding.ItemDescriptionBinding
import ru.practicum.android.diploma.search.domain.Vacancy
import ru.practicum.android.diploma.util.Debouncer
import ru.practicum.android.diploma.util.debounceClickListener
import javax.inject.Inject

class SearchAdapter(
    private val logger: Logger,
    private val debouncer: Debouncer,
) : RecyclerView.Adapter<SearchViewHolder>() {


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
        holder.itemView.debounceClickListener(debouncer) { onClick?.invoke(item) }
        holder.itemView.setOnLongClickListener { onLongClick?.invoke(item); true }

    }
}