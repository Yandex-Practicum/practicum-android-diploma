package ru.practicum.android.diploma.ui.common

import androidx.recyclerview.widget.DiffUtil

class UiModelDiffCallback<UM : UiModel<UM>> : DiffUtil.ItemCallback<UM>() {
    override fun areItemsTheSame(oldItem: UM, newItem: UM): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: UM, newItem: UM): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }

}

interface UiModel<UM> {
    fun areItemsTheSame(other: UM): Boolean
    fun areContentsTheSame(other: UM): Boolean
}
