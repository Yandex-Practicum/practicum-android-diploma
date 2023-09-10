package ru.practicum.android.diploma.search.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.databinding.ItemLoadingBinding
import ru.practicum.android.diploma.databinding.ItemSearchBinding
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.search.domain.models.Vacancy
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    private val logger: Logger,
    private val debouncer: Debouncer,
) : ListAdapter<Vacancy, RecyclerView.ViewHolder>(VacancyItemDiffCallback()) {
    
    var onClick: ((Vacancy) -> Unit)? = null
    var onLongClick: ((Vacancy) -> Unit)? = null
    
    private var isLastPage = false
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_VIEW_TYPE_ITEM) SearchViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        else LoadingViewHolder(
            ItemLoadingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchViewHolder) {
            val item = getItem(position)
            holder.bind(item)
            holder.itemView.debounceClickListener(debouncer) {
                logger.log("Adapter", "onClick($item)")
                onClick?.invoke(item)
            }
            holder.itemView.setOnLongClickListener {
                logger.log("Adapter", "onLongClick($item)")
                onLongClick?.invoke(item); true
            }
        }
    }
    
    override fun getItemViewType(position: Int): Int {
        return if (!isLastPage) {
            if (position < itemCount - 1) {
                ITEM_VIEW_TYPE_ITEM
            } else {
                ITEM_VIEW_TYPE_LOADING
            }
        } else ITEM_VIEW_TYPE_ITEM
    }
    
    fun isLastPage(flag: Boolean) {
        isLastPage = flag
    }
    
    companion object {
        private const val ITEM_VIEW_TYPE_ITEM = 0
        private const val ITEM_VIEW_TYPE_LOADING = 1
    }
}