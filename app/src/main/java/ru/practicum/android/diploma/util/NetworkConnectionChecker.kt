package ru.practicum.android.diploma.util

/**
 * Проверка доступности сетевого подключения перед выполнением запросов к API.
 */
fun interface NetworkConnectionChecker {
    fun isConnected(): Boolean
}
