package ru.practicum.android.diploma.data

interface ResourceProvider {
    fun getString(stringResource: Int): String
}