package ru.practicum.android.diploma.data

import android.content.Context
import ru.practicum.android.diploma.data.ResourceProvider

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getString(stringResource: Int): String {
        return context.getString(stringResource)
    }
}