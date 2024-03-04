package ru.practicum.android.diploma.ui.search.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class SearchLoadStateAdapter : LoadStateAdapter<BottomProgressBarViewHolder>() {
    override fun onBindViewHolder(holder: BottomProgressBarViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BottomProgressBarViewHolder {
        return BottomProgressBarViewHolder.create(parent)
    }
}
