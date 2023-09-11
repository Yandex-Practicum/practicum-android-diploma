package ru.practicum.android.diploma.search.ui.fragment.adapter_delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemLoadingBinding
import ru.practicum.android.diploma.search.ui.models.LoadingItem

class LoadingAdapterDelegate :
    DelegateAdapter<LoadingItem, LoadingAdapterDelegate.LoadingViewHolder>(LoadingItem::class.java) {
    
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingViewHolder(binding)
    }
    
    override fun bindViewHolder(
        model: LoadingItem,
        viewHolder: LoadingViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) { /* ignore */ }

    inner class LoadingViewHolder(binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root)
}
