package ru.practicum.android.diploma.favorite.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class FavoriteVacancyInteractorImpl(
    private val favoriteRepository: FavoriteVacancyRepository
) : FavoriteVacancyInteractor {
    override suspend fun insertVacancy(vacancy: Vacancy) {
        favoriteRepository.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancyById(id: String) {
        favoriteRepository.deleteVacancyById(id)
    }

    override fun getVacancies(): Flow<List<Vacancy>> =
        favoriteRepository.getVacancies()

    override fun getVacancyByID(id: String): Flow<Vacancy> =
        favoriteRepository.getVacancyByID(id)
}
