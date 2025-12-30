package ru.practicum.android.diploma.vacancy.details.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.model.Result

interface VacancyDetailsRepository {
    fun getDetailsFromApi(id: String): Flow<Result<VacancyDetail>>
    fun getDetailsFromDataBase(id: String): Flow<Result<VacancyDetail>>
}

// interface VacancyDetailsRepository {
//    fun getVacancyById(id: String): Flow<Result<VacancyDetail>>
// }
