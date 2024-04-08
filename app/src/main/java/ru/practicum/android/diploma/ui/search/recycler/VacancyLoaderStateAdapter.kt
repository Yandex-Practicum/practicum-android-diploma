package ru.practicum.android.diploma.ui.search.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyLoadStateBinding

class VacancyLoaderStateAdapter : LoadStateAdapter<VacancyLoaderStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ViewHolder(
        VacancyLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

    class ViewHolder(
        private val binding: VacancyLoadStateBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) = with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}
