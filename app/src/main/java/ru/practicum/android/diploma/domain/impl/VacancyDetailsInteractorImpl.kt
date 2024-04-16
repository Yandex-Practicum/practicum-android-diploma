package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.favorite.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsInteractorImpl(
    private val vacancyDetailsRepository: VacancyDetailsRepository,
    private val favoriteVacanciesRepository: FavoriteVacanciesRepository
) : VacancyDetailsInteractor {
    override suspend fun getVacancyDetails(id: String): Flow<VacancyDetails> {
        return vacancyDetailsRepository.getVacancyDetails(id)
    }

    override suspend fun getVacancyFromDatabase(id: String): VacancyDetails? {
        return favoriteVacanciesRepository.getVacancyById(id)
    }

    override suspend fun makeVacancyFavorite(vacancy: VacancyDetails) {
        favoriteVacanciesRepository.insertVacancy(vacancy)
    }

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return favoriteVacanciesRepository.isVacancyFavorite(vacancyId)
    }

    override suspend fun makeVacancyNormal(vacancyId: String) {
        favoriteVacanciesRepository.deleteVacancy(vacancyId)
    }
}
