package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.domain.api.IFavVacanciesInteractor
import ru.practicum.android.diploma.domain.api.IFavVacanciesRepository

class IFavVacanciesInteractorImpl(private val iFavVacanciesRepository: IFavVacanciesRepository) :
    IFavVacanciesInteractor {
    private var isChecked = false

    override suspend fun getFavorite(): Flow<List<VacancyDetails>> {
        return iFavVacanciesRepository.getAll()
    }

    override suspend fun addToFavorite(vacancy: VacancyDetails) {
        iFavVacanciesRepository.add(vacancy)
    }

    override suspend fun deleteFromFavorite(vacancy: VacancyDetails) {
        iFavVacanciesRepository.delete(vacancy)
    }

    override suspend fun isChecked(vacancyId: String): Boolean {
        getFavorite().collect { vacancyList ->
            val found = vacancyList.find { it.id == vacancyId }
            if (found != null) {
                isChecked = true
            }
        }
        return isChecked
    }
}
