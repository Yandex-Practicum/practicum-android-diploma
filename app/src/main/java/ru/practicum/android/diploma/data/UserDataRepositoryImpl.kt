package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.domain.api.UserDataRepository

class UserDataRepositoryImpl : UserDataRepository {

    override fun getAuthToken(): String = BuildConfig.API_ACCESS_TOKEN
}
