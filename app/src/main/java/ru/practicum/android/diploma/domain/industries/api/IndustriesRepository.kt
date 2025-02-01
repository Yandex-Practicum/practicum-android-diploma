package ru.practicum.android.diploma.domain.industries.api

import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Industry

interface IndustriesRepository {
    suspend fun getIndustriesList(): Resource<List<Industry>>
}
