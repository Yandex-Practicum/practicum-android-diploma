package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.InteractorFavoriteVacancies
import ru.practicum.android.diploma.domain.models.main.VacancyShort
import ru.practicum.android.diploma.domain.repositories.RepositoryFavoriteVacancies
import ru.practicum.android.diploma.presentation.favorites.ResponseDb

class InteractorFavoriteVacanciesImpl(
    private val repositoryVacancies: RepositoryFavoriteVacancies
) : InteractorFavoriteVacancies {
    override suspend fun insertVacancy(vacancy: VacancyShort): Result<Unit> {
        return repositoryVacancies.insertVacancy(vacancy)
    }

    override suspend fun getAllVacancies(): List<VacancyShort>? {
        return repositoryVacancies.getAllVacancies()
    }

    override suspend fun getById(vacancyId: Int): Result<VacancyShort> {
        return repositoryVacancies.getById(vacancyId)
    }

    override suspend fun deleteById(vacancyId: Int): Result<Unit> {
        return repositoryVacancies.deleteById(vacancyId)
    }

    override fun getVacanciesFlow(): Flow<ResponseDb<List<VacancyShort>>> {
        return repositoryVacancies.getVacanciesFlow()
    }

    override suspend fun clearDatabase(): Result<Unit> {
        return repositoryVacancies.clearDatabase()
    }
}
