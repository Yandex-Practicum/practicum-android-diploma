package ru.practicum.android.diploma.domain.impl

import android.content.Context
import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.api.ShareRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class ShareRepositoryImpl(vacancy: Vacancy) : ShareRepository {
    override fun getShareData(vacancy: Vacancy): ShareData {
        return ShareData(
            url = vacancy.id
        )
    }
}
