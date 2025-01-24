package ru.practicum.android.diploma.domain.search.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacanciesResponse

interface VacanciesInteractor {
    fun execute(): Flow<VacanciesResponse?>
}
