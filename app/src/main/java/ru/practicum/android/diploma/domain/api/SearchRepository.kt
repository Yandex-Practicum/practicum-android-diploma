package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.util.Options
import ru.practicum.android.diploma.util.ResponseData

interface SearchRepository {
    fun search(options: Options): Flow<ResponseData<VacanciesResponse>>
}
