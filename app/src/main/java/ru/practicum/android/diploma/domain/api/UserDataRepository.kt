package ru.practicum.android.diploma.domain.api

interface UserDataRepository {
    fun getAuthToken(): String
}
