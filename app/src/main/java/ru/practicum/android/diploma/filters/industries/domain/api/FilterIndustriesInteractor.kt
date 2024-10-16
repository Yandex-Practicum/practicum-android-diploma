package ru.practicum.android.diploma.filters.industries.domain.api

import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.util.network.HttpStatusCode

interface FilterIndustriesInteractor {
    fun getIndustries(): kotlinx.coroutines.flow.Flow<Pair<List<Industry>?, HttpStatusCode?>>
}
