package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.models.Vacancy

interface ShareInteractor {
    fun getShareData(id: String): ShareData
}
