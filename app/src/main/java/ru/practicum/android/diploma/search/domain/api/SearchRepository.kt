package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.VacanciesResponse
import ru.practicum.android.diploma.search.domain.utils.Options
import ru.practicum.android.diploma.search.domain.utils.VacanciesData

interface SearchRepository {
    fun search(options: Options): Flow<VacanciesData<VacanciesResponse>>
}
