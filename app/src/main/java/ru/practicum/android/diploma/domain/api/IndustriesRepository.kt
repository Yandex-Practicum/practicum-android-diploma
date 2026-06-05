package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface IndustriesRepository {
    suspend fun getIndustries(): Resource<List<Industry>>
}
