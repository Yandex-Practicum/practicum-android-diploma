package ru.practicum.android.diploma.domain.industries.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Industry

interface IndustriesInteractor {
    fun getIndustriesList(): Flow<Resource<List<Industry>>>
}
