package ru.practicum.android.diploma.domain.vacancydetails.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface GetVacancyDetailsUseCase {
    fun execute(vacancyId: String): Flow<Pair<Vacancy?, String?>>
}
