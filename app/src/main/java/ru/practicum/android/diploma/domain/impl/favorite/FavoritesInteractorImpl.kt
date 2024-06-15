package ru.practicum.android.diploma.domain.impl.favorite

import ru.practicum.android.diploma.domain.api.favorite.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.favorite.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage

class FavoritesInteractorImpl(private val repository: FavoritesRepository) : FavoritesInteractor {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        repository.addVacancyToFavorites(vacancy)
    }

    override suspend fun getVacancyById(vacancyId: String): Vacancy {
        return repository.getVacancyById(vacancyId)
    }

    override suspend fun getFavoriteVacanciesPage(limit: Int, from: Int): VacancyPage {
        return repository.getFavoriteVacanciesPage(limit, from)
    }

    override suspend fun getAllFavorites(): List<Vacancy> {
        return repository.getAllFavorites()
    }

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return repository.isVacancyFavorite(vacancyId)
    }

    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        repository.removeVacancyFromFavorites(vacancy)
    }

    override suspend fun removeVacancyById(vacancyId: String) {
        repository.removeVacancyById(vacancyId)
    }
}
