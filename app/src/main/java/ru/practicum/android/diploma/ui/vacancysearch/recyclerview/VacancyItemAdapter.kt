package ru.practicum.android.diploma.ui.vacancysearch.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel

class VacancyItemAdapter(
    private val vacancies: MutableList<VacancyUiModel>,
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
                VacancyItemViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading_footer, parent, false)
                LoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                val vacancy = vacancies[position]
                (holder as VacancyItemViewHolder).bind(vacancy)
                holder.itemView.setOnClickListener {
                    listener.onClick(vacancy.id)
                }
            }
            VIEW_TYPE_LOADING -> {
                // Ничего не делаем для футера
            }
        }
    }

    override fun getItemCount(): Int = vacancies.size + if (isLoadingAdded) 1 else 0

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
