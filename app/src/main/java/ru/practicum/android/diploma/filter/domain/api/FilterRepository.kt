package ru.practicum.android.diploma.filter.domain.api

interface FilterRepository {

    fun filter()
    suspend fun getStringFromPrefs(key: String): String

    suspend fun saveString(key: String, data: String)

    suspend fun saveCountry(key: String, data: String)
}