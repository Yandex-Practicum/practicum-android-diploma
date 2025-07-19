package ru.practicum.android.diploma.ui.vacancysearch.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel

class VacancyItemAdapter(
    private val listener: Listener
) : ListAdapter<VacancyUiModel, RecyclerView.ViewHolder>(VacancyDiffCallback) {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = inflater.inflate(R.layout.item_vacancy, parent, false)
                VacancyItemViewHolder(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = inflater.inflate(R.layout.item_loading_footer, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VacancyItemViewHolder) {
            val vacancy = getItem(position)
            holder.bind(vacancy)
            holder.itemView.setOnClickListener {
                listener.onClick(vacancy.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (isLoadingAdded) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoadingAdded && position == itemCount - 1) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        notifyItemInserted(itemCount - 1)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        notifyItemRemoved(itemCount)
    }

    interface Listener {
        fun onClick(id: String)
    }

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

object VacancyDiffCallback : DiffUtil.ItemCallback<VacancyUiModel>() {
    override fun areItemsTheSame(oldItem: VacancyUiModel, newItem: VacancyUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VacancyUiModel, newItem: VacancyUiModel): Boolean {
        return oldItem == newItem
    }
}
