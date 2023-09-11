package ru.practicum.android.diploma.search.ui.models

import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.DelegateAdapterItem

object LoadingItem : DelegateAdapterItem {
    
    private const val id: String = "0"
    override fun id(): String {
        return id
    }
    
    override fun content(): Any {
        return this
    }
}
