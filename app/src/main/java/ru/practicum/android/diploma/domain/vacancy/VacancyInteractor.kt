package ru.practicum.android.diploma.domain.vacancy

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface VacancyInteractor {
    fun getVacancyId(id: String): Flow<Resource<Vacancy>>
}
