package ru.practicum.android.diploma.search.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.databinding.ItemDescriptionBinding
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.search.domain.models.Vacancy
import javax.inject.Inject


class SearchAdapter @Inject constructor (
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
        holder.bind(item)
        holder.itemView.debounceClickListener(debouncer) {
        logger.log("Adapter", "onClick()")
            onClick?.invoke(item)
        }
        holder.itemView.setOnLongClickListener {
            logger.log("Adapter", "onLongClick()")
            onLongClick?.invoke(item); true
        }
    }
}