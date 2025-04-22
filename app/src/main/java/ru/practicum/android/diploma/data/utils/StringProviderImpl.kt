package ru.practicum.android.diploma.data.utils

import android.content.Context

class StringProviderImpl(private val context: Context) : StringProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getQuantityString(resId: Int, pluralForm: Int, count: Int): String {
        return context.resources.getQuantityString(resId, pluralForm, count)
    }
}
