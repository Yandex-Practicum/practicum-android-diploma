package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfoModel
import ru.practicum.android.diploma.filter.data.model.NetworkResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy

interface DetailsInteractor {
   suspend fun addVacancyToFavorites(vacancy: Vacancy): Flow<Unit>
   suspend fun removeVacancyFromFavorite(id: String): Flow<Int>
   suspend fun getFullVacancyInfo(id: String): Flow<NetworkResponse<VacancyFullInfoModel>>
}