package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoritesVacancyRepository
import ru.practicum.android.diploma.domain.favorites.FavoritesVacancyInteractor
import ru.practicum.android.diploma.domain.search.models.DomainVacancy

class FavoritesVacancyInteractorImpl(private val favoritesVacancyRepository: FavoritesVacancyRepository) :
    FavoritesVacancyInteractor {

    override suspend fun getAllFavoritesVacancy(): Flow<List<DomainVacancy>> {
        return favoritesVacancyRepository.getAllFavoritesVacancy()
    }

    override suspend fun getOneFavoriteVacancy(vacancyId: String): DomainVacancy {
        return favoritesVacancyRepository.getOneFavoriteVacancy(vacancyId)
    }

    override suspend fun deleteFavoriteVacancy(vacancy: DomainVacancy): Int {
        return favoritesVacancyRepository.deleteFavoriteVacancy(vacancy)
    }

    override suspend fun insertFavoriteVacancy(vacancy: DomainVacancy) {
        return favoritesVacancyRepository.insertFavoriteVacancy(vacancy)
    }

    override suspend fun getFavoriteIds(): List<String> {
        return favoritesVacancyRepository.getFavoriteIds()
    }


}
