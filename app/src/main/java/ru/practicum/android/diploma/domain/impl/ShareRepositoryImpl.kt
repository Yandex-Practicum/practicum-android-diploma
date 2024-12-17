package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.api.ShareRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class ShareRepositoryImpl(id: String) : ShareRepository {
    override fun getShareData(id: String): ShareData {
        return ShareData(
                url = "https://api.hh.ru/vacancies/$id"
        )
    }
}
