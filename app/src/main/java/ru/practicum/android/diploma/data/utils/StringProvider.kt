package ru.practicum.android.diploma.data.utils

interface StringProvider {
    fun getString(resId: Int): String
    fun getQuantityString(resId: Int, pluralForm: Int, count: Int): String
}
