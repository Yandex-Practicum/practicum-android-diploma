package ru.practicum.android.diploma.domain.favorite.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

class FavoriteInteractorImpl(
    val repository: FavoriteRepository
) : FavoriteInteractor {
    override suspend fun addVacancy(vacancy: VacancyDetail) {

        repository.addVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancyId: Int) {
        repository.deleteVacancy(vacancyId)
    }

    override fun getListVacancy(): Flow<List<VacancyDetail>> {
        return repository.getListVacancy()
    }
}
