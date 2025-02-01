package ru.practicum.android.diploma.util.diffutils

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.Industry

class IndustryDiffCallback : DiffUtil.ItemCallback<Industry>() {
    override fun areItemsTheSame(oldItem: Industry, newItem: Industry): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Industry, newItem: Industry): Boolean = oldItem == newItem
}
