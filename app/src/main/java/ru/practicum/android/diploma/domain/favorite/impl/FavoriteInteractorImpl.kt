package ru.practicum.android.diploma.domain.favorite.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDto
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository

class FavoriteInteractorImpl(
    val repository: FavoriteRepository
) : FavoriteInteractor {
    override suspend fun addVacancy(vacancy: VacancyDetailDto) {
        repository.addVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancyId: Int) {
        repository.deleteVacancy(vacancyId)
    }

    override fun getListVacancy(): Flow<List<VacancyDetailDto>> {
        return repository.getListVacancy()
    }
}
