package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.models.Vacancy

interface ShareRepository {
    fun getShareData(vacancy: Vacancy): ShareData
}
