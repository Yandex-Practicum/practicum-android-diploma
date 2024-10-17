package ru.practicum.android.diploma.filter.industry.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

interface IndustryRepositoryNetwork {
    fun getIndustriesList(): Flow<Resource<List<IndustryModel>>>
}
