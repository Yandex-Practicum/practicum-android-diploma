package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.api.ShareRepository
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy


class VacancyInteractorImpl(private val shareRepository: ShareRepository, id: String) : VacancyInteractor {
    override fun getShareData(id: String): ShareData {
        return shareRepository.getShareData(id)
    }

    override suspend fun isFavorite(vacancyId: String): Boolean {
        return shareRepository.isFavorite(vacancyId)
    }

    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        shareRepository.insertFavouritesVacancyEntity(vacancy)
    }

    override suspend fun deleteFavouritesVacancyEntity(vacancy: Vacancy) {
        shareRepository.deleteFavouritesVacancyEntity(vacancy)
    }
}
