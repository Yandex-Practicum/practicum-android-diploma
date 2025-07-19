package ru.practicum.android.diploma.domain.favouritevacancies.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.entyties.FavouriteVacancy
import ru.practicum.android.diploma.domain.favouritevacancies.repository.FavouriteVacanciesDbRepository
import ru.practicum.android.diploma.domain.favouritevacancies.usecases.FavouriteVacanciesDbInteractor
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

class FavouriteVacanciesDbInteractorImpl(
    private val favouriteVacanciesDbRepository: FavouriteVacanciesDbRepository
) : FavouriteVacanciesDbInteractor {
    override suspend fun insertVacancy(vacancy: Vacancy) {
        favouriteVacanciesDbRepository.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        favouriteVacanciesDbRepository.deleteVacancy(vacancy)
    }

    override fun getFavouriteVacancies(): Flow<List<Vacancy>> {
        return favouriteVacanciesDbRepository.getFavouriteVacancies()
            .map { list ->
                list.sortedByDescending { it.timestamp }
            }
    }

    override suspend fun getVacancyById(id: String): FavouriteVacancy? {
        return favouriteVacanciesDbRepository.getVacancyById(id)
    }
}
