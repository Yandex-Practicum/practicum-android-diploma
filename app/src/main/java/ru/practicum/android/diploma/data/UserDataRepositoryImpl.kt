package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.domain.api.UserDataRepository

class UserDataRepositoryImpl: UserDataRepository {

    override fun getAuthToken(): String {
        return BuildConfig.API_TOKEN
    }
}
