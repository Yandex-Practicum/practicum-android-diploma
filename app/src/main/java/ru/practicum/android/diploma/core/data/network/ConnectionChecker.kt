package ru.practicum.android.diploma.core.data.network

fun interface ConnectionChecker {
    fun isConnected(): Boolean
}
