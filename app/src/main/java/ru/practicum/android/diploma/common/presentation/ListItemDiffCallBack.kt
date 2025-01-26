package ru.practicum.android.diploma.common.presentation

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.search.presentation.items.ListItem

class ListItemDiffCallBack : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return when {
            oldItem is ListItem.Vacancy && newItem is ListItem.Vacancy -> oldItem.id == newItem.id
            oldItem is ListItem.LoadingItem && newItem is ListItem.LoadingItem -> true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}
