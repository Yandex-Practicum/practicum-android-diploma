package ru.practicum.android.diploma.vacancy.details.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

interface VacancyInteractor {
    fun getVacancyById(id: String): Flow<Result<VacancyDetail>>
}
