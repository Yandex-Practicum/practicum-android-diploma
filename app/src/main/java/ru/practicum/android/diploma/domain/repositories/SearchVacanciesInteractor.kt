package ru.practicum.android.diploma.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacanciesList

interface SearchVacanciesInteractor {
    fun searchVacancies(textRequest: String, page: Int): Flow<Result<VacanciesList>>
}
