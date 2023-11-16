package ru.practicum.android.diploma.presentation.search

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemLoadingBinding
import ru.practicum.android.diploma.domain.models.ErrMessage

class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
    var itemRowBinding: ItemLoadingBinding = binding

    fun bind(errMessage: ErrMessage) {
        if (errMessage.text.isNullOrEmpty()) {
            itemRowBinding.loadmoreErrorlayout.isVisible = false
            itemRowBinding.loadmoreProgress.isVisible = true
        } else {
            itemRowBinding.loadmoreErrorlayout.isVisible = true
            itemRowBinding.loadmoreProgress.isVisible = false
            itemRowBinding.loadmoreErrortxt.text = errMessage.text
        }
    }
}