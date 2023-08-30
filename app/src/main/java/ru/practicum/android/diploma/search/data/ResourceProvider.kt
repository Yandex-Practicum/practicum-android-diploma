package ru.practicum.android.diploma.search.data

interface ResourceProvider {
    fun getString(stringResource: Int): String
}