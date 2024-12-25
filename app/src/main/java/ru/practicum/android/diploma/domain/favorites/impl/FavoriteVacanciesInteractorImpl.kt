package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.favorites.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.favorites.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteVacanciesInteractorImpl(
    private val favoriteVacanciesRepository: FavoriteVacanciesRepository
) : FavoriteVacanciesInteractor {

    override suspend fun insertFavoriteVacancy(vacancyForInsert: Vacancy) {
        favoriteVacanciesRepository.insertFavoriteVacancy(vacancyForInsert)
    }

    override suspend fun deleteFavoriteVacancy(vacancyForDeleteId: String) {
        favoriteVacanciesRepository.deleteFavoriteVacancy(vacancyForDeleteId)
    }

    override fun getFavoriteVacancies(): Flow<List<Vacancy>?> {
        return favoriteVacanciesRepository.getFavoriteVacancies()
    }

    override fun getFavoriteVacancyById(vacancyId: String): Flow<Vacancy?> {
        return favoriteVacanciesRepository.getFavoriteVacancyById(vacancyId)
    }

}
