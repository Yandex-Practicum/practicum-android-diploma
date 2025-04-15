package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.InteractorFavoriteVacancies
import ru.practicum.android.diploma.domain.models.VacancyShortDmEntity
import ru.practicum.android.diploma.domain.repositories.RepositoryFavoriteVacancies

class InteractorFavoriteVacanciesImpl(
    private val repositoryVacancies: RepositoryFavoriteVacancies
) : InteractorFavoriteVacancies {
    override suspend fun insertVacancy(vacancy: VacancyShortDmEntity): Result<Unit> {
        return repositoryVacancies.insertVacancy(vacancy)
    }

    override suspend fun getAllVacancies(): List<VacancyShortDmEntity>? {
        return repositoryVacancies.getAllVacancies()
    }

    override suspend fun getById(vacancyId: Int): Result<VacancyShortDmEntity> {
        return repositoryVacancies.getById(vacancyId)
    }

    override suspend fun deleteById(vacancyId: Int): Result<Unit> {
        return repositoryVacancies.deleteById(vacancyId)
    }

    override fun getVacanciesFlow(): Flow<List<VacancyShortDmEntity>> {
        return repositoryVacancies.getVacanciesFlow()
    }

    override suspend fun clearDatabase(): Result<Unit> {
        return repositoryVacancies.clearDatabase()
    }
}
