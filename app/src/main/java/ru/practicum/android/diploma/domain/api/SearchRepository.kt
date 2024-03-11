package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.main.SearchingVacancies
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    suspend fun vacanciesPagination(params: Map<String, String>): Resource<SearchingVacancies>
}
