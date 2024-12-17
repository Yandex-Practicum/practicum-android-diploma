package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.api.ShareInteractor
import ru.practicum.android.diploma.domain.api.ShareRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class ShareInteractorImpl(private val shareRepository: ShareRepository, id: String) : ShareInteractor {
    override fun getShareData(id: String): ShareData {
        return shareRepository.getShareData(id)
    }
}
