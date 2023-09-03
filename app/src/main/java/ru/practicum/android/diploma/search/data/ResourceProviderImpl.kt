package ru.practicum.android.diploma.search.data

import android.content.Context

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getString(stringResource: Int): String {
        return context.getString(stringResource)
    }
}