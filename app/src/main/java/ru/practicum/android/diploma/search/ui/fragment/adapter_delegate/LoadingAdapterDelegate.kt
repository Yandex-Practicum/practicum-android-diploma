package ru.practicum.android.diploma.search.ui.fragment.adapter_delegate

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemLoadingBinding
import ru.practicum.android.diploma.search.ui.view_model.SCROLL_TAG

class LoadingAdapterDelegate: DelegateAdapter<LoadingItem, LoadingAdapterDelegate.LoadingViewHolder>(
    LoadingItem::class.java) {
    private var count = 0
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d(SCROLL_TAG, "createViewHolder: _____ ${++count}", )
        return LoadingViewHolder(binding)
    }


    override fun bindViewHolder(
        model: LoadingItem,
        viewHolder: LoadingViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        Log.d(SCROLL_TAG, "show loading", )
    }

    inner class LoadingViewHolder(binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root)
}


data class LoadingItem(val id : Int = 0): DelegateAdapterItem{
    override fun id(): Any {
        return id
    }

    override fun content(): Any {
        return Unit
    }
}