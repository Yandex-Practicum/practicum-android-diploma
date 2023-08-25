package ru.practicum.android.diploma.util

val <T> T.thisName: String
    get() = this!!::class.simpleName ?: "Unknown class"