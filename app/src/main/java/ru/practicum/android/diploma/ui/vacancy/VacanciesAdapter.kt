package ru.practicum.android.diploma.ui.vacancy

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.LoadingItemBinding
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.util.RecyclerViewItem

class VacanciesAdapter(private val onVacancyClicked: (String) -> Unit) :
    ListAdapter<RecyclerViewItem, RecyclerView.ViewHolder>(VacancyDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecyclerViewItem.VacancyItem -> ITEM_TYPE_VACANCY
            is RecyclerViewItem.LoadingItem -> ITEM_TYPE_LOADING
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_VACANCY -> {
                val binding =
                    VacancyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VacanciesViewHolder(binding)
            }

            ITEM_TYPE_LOADING -> {
                val binding =
                    LoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VacanciesViewHolder -> {
                val vacancyItem = getItem(position) as RecyclerViewItem.VacancyItem
                holder.bind(vacancyItem.vacancy)
                holder.itemView.setOnClickListener {
                    onVacancyClicked(vacancyItem.vacancy.vacancyId)
                }
            }

            is LoadingViewHolder -> {
                holder.bind()
            }
        }
    }

    class LoadingViewHolder(val binding: LoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            Log.d("bind", "binding")
        }
    }

    class VacancyDiffCallback : DiffUtil.ItemCallback<RecyclerViewItem>() {
        override fun areItemsTheSame(
            oldItem: RecyclerViewItem,
            newItem: RecyclerViewItem
        ): Boolean {
            return when {
                oldItem is RecyclerViewItem.VacancyItem && newItem is RecyclerViewItem.VacancyItem ->
                    oldItem.vacancy.vacancyId == newItem.vacancy.vacancyId

                oldItem is RecyclerViewItem.LoadingItem && newItem is RecyclerViewItem.LoadingItem -> true
                else -> false
            }
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: RecyclerViewItem,
            newItem: RecyclerViewItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val ITEM_TYPE_VACANCY = 0
        private const val ITEM_TYPE_LOADING = 1
    }
}
